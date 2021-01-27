/**
 * 
 */
package com.network.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author ABIJEETH
 *
 */
@SuiteClasses(value = {
	AddCommandTest.class,
	ConnectCommandTest.class,
	SetDeviceStrengthCommandTest.class,
	InfoRouteCommandTest.class,
	Scenario1Test.class
})
@RunWith(Suite.class)
public class AllTests {

}
