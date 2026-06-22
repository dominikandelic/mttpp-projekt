# Selenium testing suite for Practice software testing website

This project was created for the university course **Metode i tehnike testiranja programske podrške** at **FERIT**. 

The project is a Java Selenium TestNG framework for automated UI testing of the public demo application [Practice Software Testing - Toolshop](https://practicesoftwaretesting.com).

## Application under test

The selected application is:

- Website: https://practicesoftwaretesting.com
- Official repository: https://github.com/testsmith-io/practice-software-testing
- Type: Angular web application with REST API and Swagger documentation

Before choosing the application, several testing-practice websites were reviewed from the Ministry of Testing community resources:

- https://club.ministryoftesting.com/t/my-collection-of-dummy-sites-practical-testing-for-beginners/76939
- https://www.ministryoftesting.com/articles/websites-to-practice-testing

Practice Software Testing was selected because it provides realistic e-commerce-style flows: product catalogue, search, sorting, product details, cart, checkout, login and user account features.

## Tools and techniques used

- Java 21
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager for local browser driver setup
- RemoteWebDriver
- Official Selenium standalone Docker containers for CI browser execution
- Page Object Model
- Explicit waits
- Cross-browser support for Chrome and Firefox with parallel browser execution
- Maven Surefire reporting
- GitHub Actions CI configuration
- `.gitignore` for build and IDE files

## Test cases

The current suite contains eight automated UI test scenarios. By default, TestNG executes them in Chrome and Firefox, so `mvn test` runs 16 browser-specific test executions.

| Test | What it verifies                                  |
|---|---------------------------------------------------|
| `homePageDisplaysProducts` | Product catalogue loads successfully              |
| `searchFiltersProductsByKeyword` | Search filters products by keyword                |
| `productsCanBeSortedByLowestPrice` | Product sorting by price works                    |
| `productDetailsPageShowsSelectedProductInformation` | Product details page shows correct data           |
| `outOfStockProductCannotBeAddedToCart` | Out-of-stock products cannot be added to the cart |
| `productCanBeAddedToCart` | Product can be added to the shopping cart         |
| `productQuantityCanBeChangedBeforeAddingToCart` | Product quantity selection affects the cart total |
| `invalidLoginShowsValidationMessage` | Invalid login displays an error message           |

## Project structure

```text
src/test/java/framework
  config/       Test configuration
  driver/       WebDriver creation and browser setup
  pages/        Page Object Model classes
  tests/        TestNG test classes
```

## How to run tests

Run the full cross-browser suite in Chrome and Firefox:

```bash
mvn test
```

Run the same suite in parallel:

```bash
mvn test -DsuiteXmlFile=testng-parallel.xml
```

Run only Chrome:

```bash
mvn test -DsuiteXmlFile=testng-chrome.xml
```

Run only Firefox:

```bash
mvn test -DsuiteXmlFile=testng-firefox.xml
```

Run against a remote Selenium Grid or standalone Selenium Docker container:

```bash
mvn test -DsuiteXmlFile=testng-chrome.xml -DremoteUrl=http://localhost:4444/wd/hub
```

The base URL is intentionally fixed in the framework because the page objects and test data are written specifically for Practice Software Testing.

## Reporting

TestNG and Maven Surefire generate reports in:

```text
target/surefire-reports
```

An HTML report can also be generated with:

```bash
mvn surefire-report:report
```

## CI

The repository includes a GitHub Actions workflow in `.github/workflows/ui-tests.yml`.

It runs the Maven test suite on every push to main branch and every pull request targeting main branch.

In GitHub Actions, Chrome and Firefox run in official Selenium standalone Docker containers. The Java tests connect to those containers through `RemoteWebDriver`, which means CI does not depend on WebDriverManager or browsers installed directly on the GitHub runner.

Headless execution is available for manual runs by setting the `HEADLESS=true` environment variable.
