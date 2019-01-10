package chapter2.com.liujl.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm3 implements Realm {

    @Override
    public String getName() {
        return "myrealm3";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken; //��֧��UsernamePasswordToken���͵�Token
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();  //�õ��û���
        String password = new String((char[])token.getCredentials()); //�õ�����
        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //����û�������
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //����������
        }
        //��������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�
        return new SimpleAuthenticationInfo(username + "@163.com", password, getName());
    }
}
