package com.example.chengqi.mycoderepo.expert;

import android.content.Intent;
import android.security.KeyChain;
import android.security.KeyChainAliasCallback;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;





/*
    TODO: 1. 如何通过KeyStore的setEntry接口存储java/android方式创建的非对称密钥？（存储非对称密钥需要private key和certificate，
    TODO:    但java方式创建出来的是private key和public key，如何把public key 转化为certificate?
    TODO: 2. 通过Keytore的setEntry接口存储java方式创建的对称密钥，为什么取出来是null？ Done! 存储需要指定keystore文件
    TODO: 3. 如何通过Android keystore的方式创建SecretKey?
    TODO: 4. 如何通过Android keystore的KeyProtect接口和KeyStore SetEntry接口存储非对称和对称密钥？
    TODO: 5. 如何为通过KeyChain导入的X509中的公钥获取访问权限？
*/




public class KeyActivity extends AppCompatActivity implements KeyChainAliasCallback {

    private static final String TAG = "KeyActivity";

    private static final String ALIAS_KEY_CHAIN_PKCS12 = "alias_key_chain_pkcs12";
    private static final String ALIAS_PUBLIC_KEY_X509 = "alias_public_key_x509";

    private static final int REQUEST_INSTALL_PKCS12 = 0;
    private static final int REQUEST_INSTALL_X509 = 0;

    private static Button btnJavaPubPrivate;
    private static Button btnJavaSecureKey;
    private static Button btnAndroidPubPrivate;
    private static Button btnAndroidSecureKey;
    private static Button btnAndroidChain;
    private static Button btnAndroidImportKey;
    private static Button btnCustomSecureKey;
    private static Button btnGetPublicFromX509;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_INSTALL_PKCS12) {
            Log.d(TAG, "print key alias, result code=" + resultCode);
            KeyStore ks = null;
            try {
                ks = KeyStore.getInstance("AndroidKeyStore");
                ks.load(null);
                Enumeration<String> aliases = ks.aliases();

                while (aliases.hasMoreElements()) {
                    String s = aliases.nextElement();
                    Log.d(TAG, "alias=" + s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            KeyChain.choosePrivateKeyAlias(this, this, // Callback
                    new String[] {}, // Any key types.
                    null, // Any issuers.
                    "localhost", // Any host
                    -1, // Any port
                    ALIAS_KEY_CHAIN_PKCS12);

            Log.d(TAG, "chooser launched");
        }

        if (requestCode == REQUEST_INSTALL_X509) {
            // get public key from X.509 certificate, no need to get authority from user
            new Thread() {
                @Override
                public void run() {
                    try {
                        X509Certificate[] cersp = KeyChain.getCertificateChain(KeyActivity.this, ALIAS_PUBLIC_KEY_X509);
                        Log.d(TAG, "get certificates from X.509 KeyChain");
                        for (X509Certificate cer : cersp) {
                            Log.d(TAG, "---");
                            PublicKey pk = cer.getPublicKey();
                            printPublicKey(pk);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        btnJavaPubPrivate = (Button)findViewById(R.id.button94);
        btnJavaSecureKey = (Button)findViewById(R.id.button95);
        btnAndroidPubPrivate = (Button)findViewById(R.id.button96);
        btnAndroidSecureKey = (Button)findViewById(R.id.button97);
        btnAndroidChain = (Button)findViewById(R.id.button98);
        btnAndroidImportKey = (Button)findViewById(R.id.button99);
        btnCustomSecureKey = (Button)findViewById(R.id.button100);
        btnGetPublicFromX509 = (Button)findViewById(R.id.button101);

        btnJavaPubPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 普通的非对称加解密
                Log.d(TAG, "============= 普通的非对称加解密 =============");
                testKeyPair();
            }
        });

        btnJavaSecureKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 普通的对称加解密
                Log.d(TAG, "============= 普通的对称加解密 =============");
                testKey();
            }
        });

        btnAndroidPubPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // android keystore实现的非对称加解密
                Log.d(TAG, "============= android keystore实现的非对称加解密 =============");
                testAndroidKeyPair();
            }
        });

        btnAndroidSecureKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // android keystore实现的对称加解密
                testAndroidKey();
            }
        });

        btnAndroidChain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "============= android keystore实现的KeyChain =============");
                testKeyChain();
            }
        });

        btnAndroidImportKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "============= 通过KeyProtection导入public/private/secure key =============");
                testImportKeys();
            }
        });

        btnCustomSecureKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 自定义密码 123456 对称加密
                Log.d(TAG, "============= 自定义密码 123456 对称加密 =============");
                testCustomKeyGenerationAndSaveToKeyStore();
            }
        });

        btnGetPublicFromX509.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "============= 从x509恢复PublicKey =============");
                testReadX509();
            }
        });
    }

    private void testImportKeys() {
        Log.d(TAG, "get private key & certificate from KeyChain, this method is urgly");

        new Thread() {
            PrivateKey privateKey;
            Certificate[] certificates;

            @Override
            public void run() {
                try {
                    // get public and private key from p12 key store
                    privateKey = KeyChain.getPrivateKey(KeyActivity.this, ALIAS_KEY_CHAIN_PKCS12);

                    certificates = KeyChain.getCertificateChain(KeyActivity.this, ALIAS_KEY_CHAIN_PKCS12);

                    Log.d(TAG, "save private key & certificate to AndroidKeyStore, alias=key2");
                    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                    keyStore.load(null);
                    keyStore.setEntry(
                            "key2",
                            new KeyStore.PrivateKeyEntry(privateKey, certificates),
                            new KeyProtection.Builder(KeyProperties.PURPOSE_SIGN)
                                    .setDigests(KeyProperties.DIGEST_SHA256)
                                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                                    .setUserAuthenticationRequired(true)
                                    .setUserAuthenticationValidityDurationSeconds(10 * 60)
                                    .build());

                    Log.d(TAG, "get public/private key from AndroidKeyStore, alias=key2");
                    PrivateKey prik = (PrivateKey) keyStore.getKey("key2", null);
                    PublicKey pubK = keyStore.getCertificate("key2").getPublicKey();

                    printPublicKey(pubK);
                } catch( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void testReadX509() {
        try {
            InputStream fis = getAssets().open("client-cert.cer");
            BufferedInputStream bis = new BufferedInputStream(fis);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            while (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                Log.d(TAG, "got x.509=" + cert);
                PublicKey p = cert.getPublicKey();
                Log.d(TAG, "got public key=" + Base64.encodeToString(p.getEncoded(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testCustomKeyGenerationAndSaveToKeyStore() {
        try {
            //-------------------加密过程---------------------------------
            SecretKey keyReco;
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKeySpec keySpec = new SecretKeySpec("12345678".getBytes(), "DES");
            keyReco = skf.generateSecret(keySpec);
            Log.d(TAG, "custom key=" + new String(keyReco.getEncoded()));
            String ss = Base64.encodeToString(keyReco.getEncoded(), Base64.DEFAULT);
            Log.d(TAG, "custom key in base64=" + ss);

            //需要加密的info
            String info = "i love you baby";
            //输出加密前的密文内容
            Log.d(TAG, "++" + info);
            //产生一个Random
            SecureRandom sr = new SecureRandom();
            byte[] cipherByteEncrypt = null;
            try {
                Cipher c1 = Cipher.getInstance("DES");
                c1.init(Cipher.ENCRYPT_MODE, keyReco, sr);
                //生成密文
                cipherByteEncrypt = c1.doFinal(info.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //输出加密后的密文内容
//        System.out.println(""+new String(cipherByteEncrypt,"ISO-8859-1"));

            Log.d(TAG, "secret msg=" + new String(cipherByteEncrypt));
            //-------------------解密过程-----------------------------------
            //产生一个Random TODO:为什么？？
            sr = new SecureRandom();
            byte[] cipherByteDecrypt = null;
            try {
                Cipher c1 = Cipher.getInstance("DES");
                c1.init(Cipher.DECRYPT_MODE, keyReco, sr);
                //解析密文
                cipherByteDecrypt = c1.doFinal(cipherByteEncrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //        System.out.println(""+new String(cipherByteDecrypt,"ISO-8859-1"));
            Log.d(TAG, "--" + new String(cipherByteDecrypt));

            String path = getFilesDir().getPath();
            String keyStorePath = path + "/out.keystore";
            Log.d(TAG, "file path=" + path);

            File f = new File(keyStorePath);
            char password[] = "123456".toCharArray();
            OutputStream os = new FileOutputStream(f);

            String defaultType = KeyStore.getDefaultType();
            Log.d(TAG, "save key to " + defaultType);
            KeyStore ks = KeyStore.getInstance(defaultType);
            ks.load(null);

            KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(keyReco);
            KeyStore.ProtectionParameter parameter = new KeyStore.PasswordProtection(password);
            ks.setEntry("secretKeyAlias1", skEntry, parameter);
            ks.store(os, password);
            os.flush();
            os.close();

            File fIn = new File(keyStorePath);
            InputStream is = new FileInputStream(fIn);
            KeyStore ks2 = KeyStore.getInstance(defaultType);
            ks2.load(is, null);

            Enumeration<String> es = ks2.aliases();
            while (es.hasMoreElements()) {
                String s = es.nextElement();
                Log.d(TAG, "alias=" + s);
            }

            KeyStore.SecretKeyEntry skEntry2 = (KeyStore.SecretKeyEntry)ks2.getEntry("secretKeyAlias1", parameter);
            // TODO:why ssskey is null???????
            SecretKey ssskey = skEntry2.getSecretKey();
            Log.d(TAG, "get custom key from keystore:" + new String(ssskey.getEncoded()));
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testKeyChain() {

        Log.d(TAG, "install pkcs12");

        installPkcs12();

//        installCertificate();

        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "get private from p12 KeyChain");
                try {
                    // get public and private key from p12 key store
                    PrivateKey key = KeyChain.getPrivateKey(KeyActivity.this, ALIAS_KEY_CHAIN_PKCS12);
                    byte[] encodedByte = key.getEncoded();
                    if (encodedByte == null) {
                        Log.d(TAG, "can not get encodedByte from KeyChain's PrivateKey");
                    } else {
                        printPrivateKey(key);
                    }

                    X509Certificate[] cers = KeyChain.getCertificateChain(KeyActivity.this, ALIAS_KEY_CHAIN_PKCS12);
                    Log.d(TAG, "get certificates from p12 KeyChain");
                    for (X509Certificate cer : cers) {
                        Log.d(TAG, "---");
                        PublicKey pk = cer.getPublicKey();
                        printPublicKey(pk);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * This method will launch an intent to install the key chain
     */
    private void installPkcs12() {
        try {
            BufferedInputStream bis = new BufferedInputStream(getAssets().open("client-pkcs8.p12"));
            byte[] keychain = new byte[bis.available()];
            bis.read(keychain);
            Intent installIntent = KeyChain.createInstallIntent();
            installIntent.putExtra(KeyChain.EXTRA_PKCS12, keychain);
            installIntent.putExtra(KeyChain.EXTRA_NAME, ALIAS_KEY_CHAIN_PKCS12);
            startActivityForResult(installIntent, REQUEST_INSTALL_PKCS12);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void installCertificate() {
        try {
            BufferedInputStream bis = new BufferedInputStream(getAssets().open("client-cert.cer"));
            byte[] keychain = new byte[bis.available()];
            bis.read(keychain);
            Intent installIntent = KeyChain.createInstallIntent();
            installIntent.putExtra(KeyChain.EXTRA_CERTIFICATE, keychain);
            installIntent.putExtra(KeyChain.EXTRA_NAME, ALIAS_PUBLIC_KEY_X509);
            startActivityForResult(installIntent, REQUEST_INSTALL_X509);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 模拟器不支持，无法测试
    private void testAndroidKey() {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            //-------------------加密过程---------------------------------
            //产生一个key,需要关联一种“DES”算法
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder(
                    "aliascq-duicheng",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            SecretKey deskey = keyGenerator.generateKey();
            byte[] deskeyByte = deskey.getEncoded();
//            Log.d(TAG, "android deskeyByte=" + new String(deskeyByte));
            //需要加密的info
            String info = "12345678i love coooo";
            //输出加密前的密文内容
            Log.d(TAG, "++" + info);
            //产生一个Random
            SecureRandom sr = new SecureRandom();
            byte[] cipherByteEncrypt = null;
            try {
                Cipher c1 = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_NONE, "AndroidKeyStore");
                c1.init(Cipher.ENCRYPT_MODE, deskey, sr);
                //生成密文
                cipherByteEncrypt = c1.doFinal(info.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //输出加密后的密文内容
//        System.out.println(""+new String(cipherByteEncrypt,"ISO-8859-1"));


            //-------------------解密过程-----------------------------------
            //产生一个Random
            sr = new SecureRandom();
            byte[] cipherByteDecrypt = null;
            try {
                Cipher c1 = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7, "AndroidKeyStore");
                c1.init(Cipher.DECRYPT_MODE, deskey, sr);
                //解析密文
                cipherByteDecrypt = c1.doFinal(cipherByteEncrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //        System.out.println(""+new String(cipherByteDecrypt,"ISO-8859-1"));
            Log.d(TAG, "--" + new String(cipherByteDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printEncryptString(byte[] b) {
        String s = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d(TAG, "BASE64:" + s);
    }

    private void testKey() {
        try {
            //-------------------加密过程---------------------------------
            //产生一个key,需要关联一种“DES”算法
            Log.d(TAG, "生成密钥");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey deskey = keyGenerator.generateKey();
            printEncryptString(deskey.getEncoded());

            Log.d(TAG, "从密钥的base64恢复密钥");
            SecretKey keyReco;
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKeySpec keySpec = new SecretKeySpec(deskey.getEncoded(), "DES");
            keyReco = skf.generateSecret(keySpec);
            printEncryptString(keyReco.getEncoded());

            //需要加密的info
            String info = "12345678i love archermind";
            Log.d(TAG, "明文:" + info);
            //输出加密前的密文内容
            //产生一个Random
            SecureRandom sr = new SecureRandom();
            byte[] cipherByteEncrypt = null;
            try {
                Cipher c1 = Cipher.getInstance("DES");
                c1.init(Cipher.ENCRYPT_MODE, deskey, sr);
                //生成密文
                cipherByteEncrypt = c1.doFinal(info.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //输出加密后的密文内容
//        System.out.println(""+new String(cipherByteEncrypt,"ISO-8859-1"));
            Log.d(TAG, "密文:");
            printEncryptString(cipherByteEncrypt);

            //-------------------解密过程-----------------------------------
            //产生一个Random TODO:为什么？？
            sr = new SecureRandom();
            byte[] cipherByteDecrypt = null;
            try {
                Cipher c1 = Cipher.getInstance("DES");
                c1.init(Cipher.DECRYPT_MODE, keyReco, sr);
                //解析密文
                cipherByteDecrypt = c1.doFinal(cipherByteEncrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //        System.out.println(""+new String(cipherByteDecrypt,"ISO-8859-1"));
            Log.d(TAG, "解密:" + new String(cipherByteDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printPublicKey(PublicKey key) {
        byte[] pubEncoded = key.getEncoded();
        String pubString = Base64.encodeToString(pubEncoded, Base64.DEFAULT);
        Log.d(TAG, "public key encoded by base64 =" + pubString);
    }

    private void printPrivateKey(PrivateKey key) {
        byte[] pubEncoded = key.getEncoded();
        String pubString = Base64.encodeToString(pubEncoded, Base64.DEFAULT);
        Log.d(TAG, "private key encoded by base64 =" + pubString);
    }

    private void testAndroidKeyPair() {
        PublicKey publicKeyReco = null;

        Log.d(TAG, "通过android keystore provider创建公钥/私钥对aliascq");
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "aliascq",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
//                    .setUserAuthenticationRequired(true)
                    .build());
            KeyPair kp = kpg.generateKeyPair();
            PublicKey publickey = kp.getPublic();
            byte[] pubEncoded = publickey.getEncoded();
            String pubString = Base64.encodeToString(pubEncoded, Base64.DEFAULT);
            Log.d(TAG, "公钥明文:");
            printPublicKey(publickey);

            // TODO: how to save public key to a X.509 file?
            // TODO: how to save private key to a pkcs#8 file?
            byte[] pubDecode = Base64.decode(pubString, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubDecode);

            // KeyFactory 仅仅是用来为非对称加密的密钥进行格式转换，不可以用在对称加密密钥上
            KeyFactory keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
            publicKeyReco = keyFactory.generatePublic(keySpec);
            Log.d(TAG, "从公钥明文恢复公钥:");
            printPublicKey(publicKeyReco);
            PrivateKey privateKey = kp.getPrivate();
            if (privateKey.getEncoded() == null) {
                Log.d(TAG, "私钥明文，无权限获取");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//            kpg.initialize(new KeyGenParameterSpec.Builder(
//                    "aliascq2",
//                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
//                    .setDigests(KeyProperties.DIGEST_SHA256,
//                            KeyProperties.DIGEST_SHA512)
//                    .build());
//            kpg.generateKeyPair();

        Log.d(TAG, "获取android key store中所有密钥的alias");
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();

            while (aliases.hasMoreElements()) {
                String s = aliases.nextElement();
                Log.d(TAG, "alias=" + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            byte[] data = "i love archermind".getBytes();
            ks.containsAlias("aliascq");
            KeyStore.Entry entry = ks.getEntry("aliascq", null);
            Signature s = Signature.getInstance("SHA256withECDSA");
            Log.d(TAG, "通过alias:aliascq获取公钥/私钥");
            PrivateKey privateKeyFromKeystore = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
            Certificate cer = ks.getCertificate("aliascq");
            PublicKey ppp = cer.getPublicKey();
            printPublicKey(ppp);
//            PublicKey publicKeyFromKeystore = ((KeyStore.TrustedCertificateEntry) entry).getTrustedCertificate().getPublicKey();
//            printPublicKey(publicKeyFromKeystore);
            s.initSign(privateKeyFromKeystore);
            s.update(data);
            byte[] sig = s.sign();

            Signature ss = Signature.getInstance("SHA256withECDSA");
//            s.initVerify(publickey);
            ss.initVerify(publicKeyReco);
            ss.update(data);
            boolean valid = ss.verify(sig);
            if (valid) {
                Log.d(TAG, "签名验证通过！！");
            } else {
                Log.e(TAG, "签名验证失败！！");
            }
        } catch (UserNotAuthenticatedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testKeyPair() {
        // 生成公钥 & 私钥对
        Log.d(TAG, "生成公钥/私钥对");
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keyPairGen == null) {
            Log.e(TAG, "keyPairGen == null");
            return;
        }
        keyPairGen.initialize(1024);

//        KeyPairGenerator keyPairGen = null;
//        try {
//            keyPairGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
//            keyPairGen.initialize(new KeyGenParameterSpec.Builder(
//                    "aliascq",
//                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
//                    .setDigests(KeyProperties.DIGEST_SHA256,
//                            KeyProperties.DIGEST_SHA512)
//                    .build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
/*
3．X.509（1993）
    X.509是由国际电信联盟（ITU-T）制定的数字证书标准。在X.500确保用户名称惟一性的基础上，X.509为X.500用户名称提供了通信实体的鉴别机制，并规定了实体鉴别过程中广泛适用的证书语法和数据接口。
    X.509的最初版本公布于1988年。X.509证书由用户公共密钥和用户标识符组成。此外还包括版本号、证书序列号、CA标识符、签名算法标识、签发者名称、证书有效期等信息。这一标准的最新版本是X.509 v3，它定义了包含扩展信息的数字证书。该版数字证书提供了一个扩展信息字段，用来提供更多的灵活性及特殊应用环境下所需的信息传送。
4．PKCS系列标准
    PKCS是由美国RSA数据安全公司及其合作伙伴制定的一组公钥密码学标准，其中包括证书申请、证书更新、证书作废表发布、扩展证书内容以及数字签名、数字信封的格式等方面的一系列相关协议。到1999年底，PKCS已经公布了以下标准：
    PKCS#1：定义RSA公开密钥算法加密和签名机制，主要用于组织PKCS#7中所描述的数字签名和数字信封。
    PKCS#3：定义Diffie-Hellman密钥交换协议。
    PKCS#5：描述一种利用从口令派生出来的安全密钥加密字符串的方法。使用MD2或MD5 从口令中派生密钥，并采用DES-CBC模式加密。主要用于加密从一个计算机传送到另一个计算机的私人密钥，不能用于加密消息。
    PKCS#6：描述了公钥证书的标准语法，主要描述X.509证书的扩展格式。
    PKCS#7：定义一种通用的消息语法，包括数字签名和加密等用于增强的加密机制，PKCS#7与PEM兼容，所以不需其他密码操作，就可以将加密的消息转换成PEM消息。
    PKCS#8：描述私有密钥信息格式，该信息包括公开密钥算法的私有密钥以及可选的属性集等。
    PKCS#9：定义一些用于PKCS#6证书扩展、PKCS#7数字签名和PKCS#8私钥加密信息的属性类型。
    PKCS#10：描述证书请求语法。
    PKCS#11：称为Cyptoki，定义了一套独立于技术的程序设计接口，用于智能卡和PCMCIA卡之类的加密设备。
    PKCS#12：描述个人信息交换语法标准。描述了将用户公钥、私钥、证书和其他相关信息打包的语法。
    PKCS#13：椭圆曲线密码体制标准。
    PKCS#14：伪随机数生成标准。
    PKCS#15：密码令牌信息格式标准。
 */

        KeyPair keyPair = keyPairGen.generateKeyPair();
        // format X.509
        PublicKey publicKey = (PublicKey) keyPair.getPublic();
        // format PKCS#8
        PrivateKey privateKey = (PrivateKey) keyPair.getPrivate();

        Log.d(TAG, "从PublicKey/PrivateKey对象获取公钥/私钥明文");
        // get key string, this byte[] can be saved to build a public key file or private key file
        byte[] pubEncoded = publicKey.getEncoded();
        String pubString = Base64.encodeToString(pubEncoded, Base64.DEFAULT);

        byte[] priEncoded = privateKey.getEncoded();
        String priString = Base64.encodeToString(priEncoded, Base64.DEFAULT);

        Log.d(TAG, "public key base64 str=" + pubString);
        Log.d(TAG, "private key base64 str=" + priString);

        Log.d(TAG, "public key format=" + publicKey.getFormat() + " encode=" + publicKey.getEncoded() + " length=" + publicKey.getEncoded().length);
        Log.d(TAG, "private key format=" + privateKey.getFormat() + " encode=" + privateKey.getEncoded() + " length=" + privateKey.getEncoded().length);

        PublicKey publicKeyReco = null;
        PrivateKey privateKeyReco = null;

//        KeyStore outputKeyStore = KeyStore.getInstance("JKS");

        Log.d(TAG, "从公钥/私钥明文生成PublicKey/PrivateKey对象");
        // build Public key from string
        byte[] pubDecode = Base64.decode(pubString, Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubDecode);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            publicKeyReco = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // build private key from string
        byte[] priDecode = Base64.decode(priString, Base64.DEFAULT);
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(priDecode);
        try {
            KeyFactory kf = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            privateKeyReco = kf.generatePrivate(ks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 私钥签名
        Signature signature = null;
        try {
            signature = Signature.getInstance("MD5withRSA");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (signature == null) {
            return;
        }

        String originText = "i am plain text";
        Log.d(TAG, "测试用明文:" + originText);

        Log.d(TAG, "用原有的私钥签名");
        byte[] plain = originText.getBytes();
        byte[] sig;
        try {
            signature.initSign(privateKey);
            signature.update(plain);
            sig = signature.sign();
            Log.d(TAG, "signature=" + sig);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Log.d(TAG, "用生成的公钥验证");
        // 公钥验证
        Signature signature2;
        try {
            signature2 = Signature.getInstance("MD5withRSA");
            signature2.initVerify(publicKeyReco);
            signature2.update(plain);
            boolean result = signature2.verify(sig);
            if (!result) {
                Log.e(TAG, "验证失败！！");
            } else {
                Log.d(TAG, "验证成功！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Log.d(TAG, "用生成的私钥加密");
        // 私钥/公钥加密
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(privateKeyReco.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKeyReco);
            int inputLen = plain.length;
            encryptedData = cipher.doFinal(plain, 0, plain.length);
            String encryptedStr = Base64.encodeToString(encryptedData, Base64.DEFAULT);
            Log.d(TAG, "encryptedStr=" + encryptedStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "用原有的公钥解密");
        // 私钥/公钥解密
        try {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            int inputLen = encryptedData.length;
            byte[] cache;
            cache = cipher.doFinal(encryptedData, 0, encryptedData.length);
            byte[] rr = new byte[]{'c','h','e','n','g'};

            String s = new String(cache);
            Log.d(TAG, "decryptedStr=" + new String(cache) + "=" + new String(rr));
            if (originText.equals(s)) {
                Log.d(TAG, "解密成功！！");
            } else {
                Log.d(TAG, "解密失败！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alias(String alias) {
        Log.d(TAG, "reset alias to " + alias);
    }
}

