package chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticatorTest {
	
	Logger logger = null;
 	
	@Before
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	private void login(String configFile) {
        //1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory(configFile);

        //2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {  
	        //4����¼���������֤  
	        subject.login(token);
	        System.out.println("login successed");
	    } catch (AuthenticationException e) {  
	        //5�������֤ʧ��  
	    	System.out.println("login failed");
	    }  
    }
    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ��������Realm��֤�ɹ��������Ϣ
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("classpath:shiro-authenticator-all-fail.ini");
    }

    @Test
    public void testAtLeastOneSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastOne-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ��������Realm��֤�ɹ��������Ϣ
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    @Test
    public void testFirstOneSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-first-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ�������˵�һ��Realm��֤�ɹ��������Ϣ
        PrincipalCollection principalCollection = subject.getPrincipals();
        logger.info(principalCollection.getRealmNames().toString());
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    @Test
    public void testAtLeastTwoStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ���ΪmyRealm1��myRealm4���ص����һ���������ʱֻ����һ��
        PrincipalCollection principalCollection = subject.getPrincipals();
        logger.info(principalCollection.getRealmNames().toString());
        logger.info(principalCollection.asList().size()+"");
        for (Object object : principalCollection.asList()) {
			logger.info(object.toString());
		}
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    @Test
    public void testOnlyOneStrategyWithSuccess() {
        login("classpath:shiro-authenticator-onlyone-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ���ΪmyRealm1��myRealm4���ص����һ���������ʱֻ����һ��
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//�˳�ʱ������Subject���߳� ������´β������Ӱ��
    }
}
