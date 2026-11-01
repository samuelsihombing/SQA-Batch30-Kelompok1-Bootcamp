package com.hadir.pages.webadmin;

import org.openqa.selenium.*;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileNavigationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators Navbar & Hamburger / Drawer (menggunakan local-name()='svg' untuk menangani namespace SVG di XPath)
    private By hamburgerButton = By.xpath("//header//button[.//*[local-name()='svg' and contains(@class, 'feather-menu')]]");
    private By desktopArrowButton = By.xpath("//header//button[.//*[local-name()='svg' and @data-testid='ArrowLeftIcon']]");
    private By dockedSidebar = By.xpath("//div[contains(@class, 'sidebar') and not(ancestor::div[contains(@class, 'MuiDrawer-modal')])] | //div[contains(@class, 'MuiDrawer-paperAnchorDockedLeft')]");
    private By modalDrawer = By.cssSelector(".MuiDrawer-modal");
    private By visibleBackdrop = By.cssSelector(".MuiBackdrop-root:not(.MuiBackdrop-invisible)");
    private By laporanMenuInDrawer = By.xpath("//div[contains(@class, 'MuiDrawer-modal')]//div[contains(@class, 'sidebar__item')]//p[normalize-space()='Laporan']");

    // Locators Filter & Kontrol Halaman
    private By searchInput = By.id("search");
    private By startDateInput = By.cssSelector("input[placeholder='Start Date']");
    private By endDateInput = By.cssSelector("input[placeholder='End Date']");
    private By searchButton = By.cssSelector("button[type='submit']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By calendarIconButton = By.xpath("//input[@placeholder='Start Date']/following-sibling::div//button");
    private By datePickerWrapper = By.cssSelector(".rdrDateRangeWrapper, .date__picker");

    // Locators Tabel & Pagination
    private By tableContainer = By.cssSelector(".MuiTableContainer-root");
    private By paginationRoot = By.cssSelector(".MuiTablePagination-root");
    private By paginationText = By.cssSelector(".MuiTablePagination-displayedRows");
    private By paginationNextButton = By.cssSelector(".MuiTablePagination-actions button:last-child");
    private By paginationPrevButton = By.cssSelector(".MuiTablePagination-actions button:first-child");

    public MobileNavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Pengaturan Viewport CDP ---
    public void setViewport(int width, int height) {
        if (driver instanceof ChromiumDriver) {
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("width", width);
            metrics.put("height", height);
            metrics.put("deviceScaleFactor", 1);
            metrics.put("mobile", width < 1000);
            ((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
        } else {
            driver.manage().window().setSize(new Dimension(width, height));
        }
    }

    public void resetViewport() {
        try {
            if (driver instanceof ChromiumDriver) {
                ((ChromiumDriver) driver).executeCdpCommand("Emulation.clearDeviceMetricsOverride", new HashMap<>());
            }
        } catch (Exception ignored) {}
        try {
            driver.manage().window().setSize(new Dimension(1366, 768));
        } catch (Exception ignored) {}
    }

    public int getInnerWidth() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long w = (Long) js.executeScript("return window.innerWidth;");
        return w != null ? w.intValue() : 0;
    }

    public int getInnerHeight() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long h = (Long) js.executeScript("return window.innerHeight;");
        return h != null ? h.intValue() : 0;
    }

    // --- Verifikasi Sidebar & Hamburger ---
    public boolean isSidebarDockedVisible() {
        try {
            List<WebElement> elements = driver.findElements(dockedSidebar);
            for (WebElement el : elements) {
                if (el.isDisplayed() && el.getSize().getWidth() > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHamburgerButtonDisplayed() {
        try {
            WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(hamburgerButton));
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickHamburgerButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(hamburgerButton));
        btn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalDrawer));
    }

    public boolean isDrawerOpen() {
        try {
            WebElement drawer = wait.until(ExpectedConditions.visibilityOfElementLocated(modalDrawer));
            return drawer.isDisplayed() && drawer.getSize().getWidth() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeDrawerViaBackdrop() {
        WebElement backdrop = wait.until(ExpectedConditions.presenceOfElementLocated(visibleBackdrop));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backdrop);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalDrawer));
    }

    public boolean isDrawerClosed() {
        try {
            return wait.until(d -> {
                List<WebElement> drawers = d.findElements(modalDrawer);
                if (drawers.isEmpty()) {
                    return true;
                }
                WebElement dr = drawers.get(0);
                return !dr.isDisplayed() || "true".equals(dr.getAttribute("aria-hidden"));
            });
        } catch (Exception e) {
            return true;
        }
    }

    public void expandLaporanMenuInDrawer() {
        WebElement drawer = wait.until(ExpectedConditions.visibilityOfElementLocated(modalDrawer));
        List<WebElement> submenus = drawer.findElements(By.xpath(".//div[contains(@class, 'sidebar__wrapper')]//p[normalize-space()='Semua']"));
        if (submenus.isEmpty() || !submenus.get(0).isDisplayed()) {
            WebElement laporan = wait.until(ExpectedConditions.elementToBeClickable(laporanMenuInDrawer));
            laporan.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'MuiDrawer-modal')]//p[normalize-space()='Semua']")));
        }
    }

    public void clickSubmenuInDrawer(String submenuName) {
        expandLaporanMenuInDrawer();
        By subLocator = By.xpath("//div[contains(@class, 'MuiDrawer-modal')]//p[normalize-space()='" + submenuName + "']");
        WebElement subItem = wait.until(ExpectedConditions.elementToBeClickable(subLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subItem);
        subItem.click();
    }

    public boolean isSubmenuActiveInDrawer(String submenuName, String expectedPath) {
        boolean urlMatches = wait.until(ExpectedConditions.urlContains(expectedPath));
        if (!urlMatches) {
            return false;
        }

        clickHamburgerButton();
        expandLaporanMenuInDrawer();

        By subLocator = By.xpath("//div[contains(@class, 'MuiDrawer-modal')]//p[normalize-space()='" + submenuName + "']/ancestor::div[contains(@class, 'MuiBox-root')][1]");
        WebElement subItemBox = wait.until(ExpectedConditions.visibilityOfElementLocated(subLocator));
        String classAttr = subItemBox.getAttribute("class");

        closeDrawerViaBackdrop();

        return classAttr != null && !classAttr.isEmpty();
    }

    // --- Verifikasi Horizontal Overflow & Layout Filter ---
    public boolean hasPageHorizontalOverflow() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean overflow = (Boolean) js.executeScript(
            "var body = document.body;" +
            "var html = document.documentElement;" +
            "var scrollW = Math.max(body.scrollWidth, html.scrollWidth);" +
            "return scrollW > window.innerWidth;"
        );
        return Boolean.TRUE.equals(overflow);
    }

    public boolean areAllFilterControlsVisible() {
        try {
            boolean sIn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).isDisplayed();
            boolean stDate = wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput)).isDisplayed();
            boolean enDate = wait.until(ExpectedConditions.visibilityOfElementLocated(endDateInput)).isDisplayed();
            boolean sBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).isDisplayed();
            boolean rBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed();
            return sIn && stDate && enDate && sBtn && rBtn;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFilterStackedVertically() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement sIn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        WebElement sBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));

        Map<String, Object> inRect = (Map<String, Object>) js.executeScript("var r = arguments[0].getBoundingClientRect(); return {top: r.top, height: r.height};", sIn);
        Map<String, Object> btnRect = (Map<String, Object>) js.executeScript("var r = arguments[0].getBoundingClientRect(); return {top: r.top};", sBtn);

        double inBottom = ((Number) inRect.get("top")).doubleValue() + ((Number) inRect.get("height")).doubleValue();
        double btnTop = ((Number) btnRect.get("top")).doubleValue();

        return btnTop > inBottom;
    }

    // --- Verifikasi & Aksi Filter di Mobile ---
    public void enterSearchText(String text) {
        WebElement in = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        in.clear();
        in.sendKeys(text);
    }

    public String getSearchInputValue() {
        WebElement in = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        return in.getAttribute("value");
    }

    public void clickSearchButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        btn.click();
    }

    public void clickResetButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        btn.click();
    }

    // --- Verifikasi Date Picker di Mobile ---
    public void openDatePickerOnMobile() {
        WebElement iconBtn = wait.until(ExpectedConditions.elementToBeClickable(calendarIconButton));
        iconBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(datePickerWrapper));
    }

    public boolean isDatePickerPopoverWithinViewport() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement popover = wait.until(ExpectedConditions.visibilityOfElementLocated(datePickerWrapper));
        Map<String, Object> rect = (Map<String, Object>) js.executeScript(
            "var r = arguments[0].getBoundingClientRect();" +
            "return {" +
            "  fits: (r.right <= window.innerWidth && r.left >= 0)," +
            "  width: r.width," +
            "  innerWidth: window.innerWidth" +
            "};", popover
        );
        return Boolean.TRUE.equals(rect.get("fits"));
    }

    public void selectDateRangeOnMobile(int startDayIdx, int endDayIdx) {
        WebElement popover = wait.until(ExpectedConditions.visibilityOfElementLocated(datePickerWrapper));
        List<WebElement> activeDays = popover.findElements(By.cssSelector(".rdrDay:not(.rdrDayPassive)"));
        if (activeDays.size() > Math.max(startDayIdx, endDayIdx)) {
            activeDays.get(startDayIdx).click();
            activeDays.get(endDayIdx).click();
        }
    }

    public String getStartDateValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput)).getAttribute("value");
    }

    public String getEndDateValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(endDateInput)).getAttribute("value");
    }

    // --- Verifikasi Tabel Horizontal Scrolling di Mobile ---
    public boolean canTableScrollHorizontally() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement tc = wait.until(ExpectedConditions.visibilityOfElementLocated(tableContainer));
        Boolean canScroll = (Boolean) js.executeScript(
            "return (arguments[0].scrollWidth > arguments[0].clientWidth);", tc
        );
        return Boolean.TRUE.equals(canScroll);
    }

    public boolean scrollTableHorizontally(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement tc = wait.until(ExpectedConditions.visibilityOfElementLocated(tableContainer));
        js.executeScript("arguments[0].scrollLeft = arguments[1];", tc, pixels);
        Long scrolledLeft = (Long) js.executeScript("return Math.round(arguments[0].scrollLeft);", tc);
        js.executeScript("arguments[0].scrollLeft = 0;", tc);
        return scrolledLeft != null && scrolledLeft > 0;
    }

    // --- Verifikasi Pagination di Mobile ---
    public boolean isPaginationDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(paginationRoot)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPaginationText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(paginationText)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isNextPageButtonEnabled() {
        try {
            WebElement btn = driver.findElement(paginationNextButton);
            return btn.isEnabled() && !"true".equals(btn.getAttribute("disabled"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPrevPageButtonEnabled() {
        try {
            WebElement btn = driver.findElement(paginationPrevButton);
            return btn.isEnabled() && !"true".equals(btn.getAttribute("disabled"));
        } catch (Exception e) {
            return false;
        }
    }

    public void clickNextPage() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(paginationNextButton));
        btn.click();
    }

    public void clickPreviousPage() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(paginationPrevButton));
        btn.click();
    }
}
