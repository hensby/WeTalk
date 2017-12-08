package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class FailReason {
    private final FailReason.FailType type;
    private final Throwable cause;

    public FailReason(FailReason.FailType type, Throwable cause) {
        this.type = type;
        this.cause = cause;
    }

    public FailReason.FailType getType() {
        return this.type;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public static enum FailType {
        IO_ERROR,
        DECODING_ERROR,
        NETWORK_DENIED,
        OUT_OF_MEMORY,
        UNKNOWN;

        private FailType() {
        }
    }
}
