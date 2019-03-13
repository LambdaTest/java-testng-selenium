package com.lambdatest.Tests;

import java.util.List;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

public class SuiteAlterer implements IAlterSuiteListener {

 

    @Override
    public void alter(List<XmlSuite> suites) {

        int count = Integer.parseInt(System.getProperty("threadcount", "-1"));

        XmlSuite suite = suites.get(0);

        suite.setDataProviderThreadCount(count);

    }

	public void altera(List<XmlSuite> suites) {
		// TODO Auto-generated method stub
		
	}

}


