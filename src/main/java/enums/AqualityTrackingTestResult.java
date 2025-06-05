package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AqualityTrackingTestResult {
    FAILED(1, 2),
    PASSED(2, 1),
    SKIPPED(3, 3);

    private final int status;
    private final int iTestResultStatus;

    public static int getStatusByITestResultStatus(int iTestResultStatus) {
        return Arrays.stream(values())
                .filter(value -> value.getITestResultStatus() == iTestResultStatus)
                .map(AqualityTrackingTestResult::getStatus)
                .findFirst()
                .orElseThrow();
    }
}
