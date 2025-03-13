package yerong.wedle.community.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class CommunityNotFoundException extends CustomException {

    public CommunityNotFoundException() {
        super(ResponseCode.COMMUNITY_NOT_FOUND);
    }
}
