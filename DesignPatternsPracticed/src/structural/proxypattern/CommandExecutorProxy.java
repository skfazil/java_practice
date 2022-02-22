package structural.proxypattern;

//Using this proxy class, we restric the unauthorized user
public class CommandExecutorProxy implements CommandExecutor{
	boolean isAdmin=false;
	CommandExecutor executor;
	
	public CommandExecutorProxy(String username, String password) {
		if(username.equals("fazil")&&password.equals("fazil")) {
			isAdmin=true;
		}
		executor=new CommandExecutorImpl();
	}

	@Override
	public void runCommand(String cmd) {
		if(isAdmin) {
			executor.runCommand(cmd);
		}
		else {
			if(!cmd.trim().startsWith("rm")) {
				executor.runCommand(cmd);
			}
			else
				System.out.println("User doesnt have permissions to run the command");
		}
	}

}
