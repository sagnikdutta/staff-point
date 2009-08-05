package ru.point.func;

import com.thoughtworks.selenium.*;

/**
 * @author Mikhail Sedov [03.08.2009]
 */
public class BasicTest extends SeleneseTestCase {

	public void setUp() throws Exception {
		setUp("http://localhost/", "*chrome");
	}

	public void testUntitled() throws Exception {
		selenium.open("/");
		selenium.click("link=Java Team");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=����� ��������");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=exact:��� ��?");
		selenium.waitForPageToLoad("30000");
		selenium.type("login", "abramova.irina@devetel.com");
		selenium.type("password", "123");
		selenium.click("link=�����");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=����������");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=�������");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=�������������");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=�����");
		selenium.waitForPageToLoad("30000");
	}
}

