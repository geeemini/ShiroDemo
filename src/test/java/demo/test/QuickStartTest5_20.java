package demo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * spring boot 整合shiro  测试第一课
 * @beforeClass  -> @before -> @test -> @after ->　@ afterClass
 */
public class QuickStartTest5_20 {

    /**
     * 1、 创建SecurityManager环境
     * 2、 调用Subject.login()方法
     * 3、 SecurityManage进行认证
     * 4、 Authenticator 执行认证
     * 5、 根据Realm进行验证
     */
    // 创建SecurityManager环境
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
    // 创建Realm
    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    @Before
    public void init(){

        //初始化数据源
        accountRealm.addAccount("lxx","520");
        accountRealm.addAccount("yw","521");

        //构建环境
        // 把SecurityManager和Realm相关联
        defaultSecurityManager.setRealm(accountRealm);
    }

    @Test
    public void testAuthentication(){
        // SecurityManager环境加入
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //拿到当前操作用户的主体
        Subject subject = SecurityUtils.getSubject();

        //把用户输入的账号密码打包成一个token
        UsernamePasswordToken  usernamePasswordToken =
                new UsernamePasswordToken("yw","521");
        subject.login(usernamePasswordToken);

        System.out.println(subject.isAuthenticated());

    }

}
