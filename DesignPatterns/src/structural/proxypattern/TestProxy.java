package structural.proxypattern;

public class TestProxy {
	
	public static void main(String[] args) {
		CommandExecutor executor = new CommandExecutorProxy("fazil", "wrong_pwd");
		executor.runCommand("ls -lrt");
		
		CommandExecutor executor2 = new CommandExecutorProxy("fazil", "wrong_pwd");
		executor2.runCommand("rm -f");
	}

}
