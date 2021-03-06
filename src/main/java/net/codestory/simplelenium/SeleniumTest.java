/**
 * Copyright (C) 2013-2015 all@code-story.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.codestory.simplelenium;

import net.codestory.simplelenium.driver.PhantomJSDriver;
import net.codestory.simplelenium.rules.InjectPageObjects;
import net.codestory.simplelenium.rules.PrintTestName;
import net.codestory.simplelenium.rules.TakeSnapshot;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.openqa.selenium.Dimension;

import static org.junit.rules.RuleChain.outerRule;

public abstract class SeleniumTest implements SectionObject {
  private final PrintTestName printTestName = new PrintTestName();
  private final InjectPageObjects injectPageObjects = new InjectPageObjects(this);
  private final TakeSnapshot takeSnapshot = new TakeSnapshot();

  @Rule
  public RuleChain ruleChain = outerRule(printTestName).around(injectPageObjects).around(takeSnapshot);

  protected SeleniumTest() {
    configureWebDriver(driver());
  }

  protected void configureWebDriver(PhantomJSDriver driver) {
    driver.manage().window().setSize(new Dimension(2048, 768));
  }

  // Override to set a base url
  protected String getDefaultBaseUrl() {
    return "";
  }

  public SeleniumTest takeSnapshot() {
    takeSnapshot.takeSnapshot();
    return this;
  }

  public SeleniumTest goTo(String url) {
    Navigation.setBaseUrl(getDefaultBaseUrl());
    SectionObject.super.goTo(url);
    return this;
  }
}
