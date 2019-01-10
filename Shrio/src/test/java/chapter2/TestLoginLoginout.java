package chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoginLoginout{
	
	Logger logger = null;
 	
	@Before
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@Test  
	public void testHelloworld() {  
		
	    //1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager  
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory("classpath:shiro.ini");  
	
	    //2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	    
	    //3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��  
	    Subject subject = SecurityUtils.getSubject();  
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");  
	  
	    try {  
	        //4����¼���������֤  
	        subject.login(token);
	        System.out.println("ok");
	    } catch (AuthenticationException e) {  
	        //5�������֤ʧ��  
	    	System.out.println("shibai");
	    }  
	  
	    Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼  
	  
	    //6���˳�  
	    subject.logout();  
	}  
	
	/**
	 * �����Զ���realm
	 */
	 @Test
	    public void testCustomRealm() {
	        //1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
	        Factory<org.apache.shiro.mgt.SecurityManager> factory =
	                new IniSecurityManagerFactory("classpath:shiro-realm.ini");

	        //2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
	        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);

	        //3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
	        Subject subject = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

	        try {
	            //4����¼���������֤
	            subject.login(token);
	        } catch (AuthenticationException e) {
	            //5�������֤ʧ��
	            e.printStackTrace();
	        }

	        Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼

	        //6���˳�
	        subject.logout();
	    }

	    @Test
	    public void testCustomMultiRealm() {
	        //1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
	        Factory<org.apache.shiro.mgt.SecurityManager> factory =
	                new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");

	        //2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
	        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);

	        //3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
	        Subject subject = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");

	        try {
	            //4����¼���������֤
	            subject.login(token);
	        } catch (AuthenticationException e) {
	            //5�������֤ʧ��
	            e.printStackTrace();
	        }

	        Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼

	        //6���˳�
	        subject.logout();
	    }


	    @Test
	    public void testJDBCRealm() {
	        //1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
	        Factory<org.apache.shiro.mgt.SecurityManager> factory =
	                new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

	        //2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
	        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);

	        //3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
	        Subject subject = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken("zz", "123");

	        try {
	            //4����¼���������֤
	            subject.login(token);
	        } catch (AuthenticationException e) {
	            //5�������֤ʧ��
	        	logger.info("��½ʧ�ܣ�");
//	            e.printStackTrace();
	        }

	        Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼

	        //6���˳�
	        subject.logout();
	    }


	    @After
	    public void tearDown() throws Exception {
	        ThreadContext.unbindSubject();//�˳�ʱ������Subject���߳� ������´β������Ӱ��
	    }
}
