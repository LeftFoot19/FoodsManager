package main;

import java.util.Properties;

import org.python.util.PythonInterpreter;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8");
        props.put("python.path", "python_sourse");

        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
        try (PythonInterpreter interp = new PythonInterpreter()) {

        	interp.exec("imo");
            interp.exec("a =  2");
            interp.exec("print(a)");


        }
	}

}
