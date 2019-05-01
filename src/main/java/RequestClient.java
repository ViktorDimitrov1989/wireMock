import dto.ResponseDto;
import feign.RequestLine;

public interface RequestClient {

    @RequestLine("GET /test/getDataFromServer")
    ResponseDto sendGetRequest();
}
