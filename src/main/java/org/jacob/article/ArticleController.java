package org.jacob.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

	@Autowired
	ArticleDao articleDao;

	/**
	 * 글 목록
	 */
	@GetMapping("/article/list")
	public void articleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Article> articleList = articleDao.listArticles(offset, COUNT);
		int totalCount = articleDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}

	/**
	 * 글 보기
	 */
	@GetMapping("/article/view")
	public void articleView(@RequestParam("articleId") String articleId,
			Model model) {
		Article article = articleDao.getArticle(articleId);
		model.addAttribute("article", article);
	}

	/**
	 * 글 등록 화면
	 */
	@GetMapping("/article/addForm")
	public void articleAddForm() {
	}

	/**
	 * 글 등록
	 */
	@PostMapping("/article/add")
	public String articleAdd(Article article) {
		article.setUserId("2017041070");
		article.setName("김솔이");
		articleDao.addArticle(article);
		return "redirect:/app/article/list";
	}
}
