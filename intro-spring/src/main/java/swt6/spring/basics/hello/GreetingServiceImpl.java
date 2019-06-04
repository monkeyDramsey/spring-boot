package swt6.spring.basics.hello;

public class GreetingServiceImpl implements GreetingService{

    private String message;

    public GreetingServiceImpl(){
    }

    public GreetingServiceImpl(String str){
        this.message=str;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
