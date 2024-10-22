package yerong.wedle.category.questionnaire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.questionnaire.dto.MatchingResultResponse;
import yerong.wedle.category.questionnaire.dto.QuestionnaireResponse;
import yerong.wedle.category.questionnaire.service.MatchingService;

import java.util.List;

@Tag(name = "학과 매칭 설문 API", description = "설문조사를 통한 학과 매칭 서비스 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questionnaires")
public class MatchingApiController {
    private final MatchingService matchingService;

    @Operation(summary = "모든 카테고리의 설문지를 조회",
            description = "각 카테고리에 속하는 모든 설문 질문을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문지가 성공적으로 반환됨"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public List<QuestionnaireResponse> getAllQuestionnairesByCategory() {
        return matchingService.getAllByCategory();
    }

    @Operation(summary = "점수에 따른 매칭 결과 조회",
            description = "주어진 점수에 해당하는 추천 학과와 설명을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매칭 결과가 성공적으로 반환됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "결과를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/results")
    public MatchingResultResponse getMatchingResult(@RequestParam int score) {
        return matchingService.getMatchingResult(score);
    }
}