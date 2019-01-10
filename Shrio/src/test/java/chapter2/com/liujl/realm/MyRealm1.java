package chapter2.com.liujl.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
/**
 * �Զ���realmʵ��
 * @author foresee
 *
 */
public class MyRealm1 implements Realm {  
    @Override  
    public String getName() {  
        return "myrealm1";  
    }  
    @Override  
    public boolean supports(AuthenticationToken token) {  
        //��֧��UsernamePasswordToken���͵�Token  
        return token instanceof UsernamePasswordToken;   
    }  
    @Override  
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
    	//�õ��û���  
        String username = (String)token.getPrincipal();  
      //�õ�����  
        String password = new String((char[])token.getCredentials()); 
        if(!"zhang".equals(username)) {  
        	//����û�������  
            throw new UnknownAccountException(); 
        }  
        if(!"123".equals(password)) {  
        	//����������  
            throw new IncorrectCredentialsException(); 
        }  
        //���������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�  
        return new SimpleAuthenticationInfo(username, password, getName());  
    }  
}   