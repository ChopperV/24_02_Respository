package com.KoreaIT.java.AM;

import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static List<Article> articles;

  static {
    articles = new ArrayList<>();
  }

  public static void main(String[] args) {
    System.out.println("== 프로그램 시작 ==");
    makeTestData();
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.print("명령어 ) ");
      String cmd = sc.nextLine().trim();

      if (cmd.length() == 0) {
        System.out.println("명령어를 입력하세요.");
        continue;
      }

      if (cmd.equals("system exit")) {
        break;
      }

      if (cmd.equals("article write")) {
        int id = articles.size() + 1;
        String regDate = Util.getNowDateStr();
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, title, body);
        articles.add(article);

        System.out.printf("%d번 글이 생성되었습니다.\n", id);

      } else if (cmd.equals("article list")) {
        if (articles.size() == 0) {
          System.out.println("게시글이 없습니다.");
          continue;
        } else {
          System.out.println(" 번호 |   제목   | 조회수 ");
          for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("  %2d  |  %6s  |  %2d  \n", article.id, article.title, article.viewCnt);
          }
        }

      } else if (cmd.startsWith("article detail ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = null;

        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundArticle = article;
            break;
          }
        }

        if (foundArticle == null) {
          System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
          continue;
        } else {
          foundArticle.increaseViewCnt();
          System.out.printf("번호 : %d\n", foundArticle.id);
          System.out.printf("날짜 : %s\n", foundArticle.regDate);
          System.out.printf("제목 : %s\n", foundArticle.title);
          System.out.printf("내용 : %s\n", foundArticle.body);
          System.out.printf("조회수 : %d\n", foundArticle.viewCnt);
        }

      } else if (cmd.startsWith("article delete ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundIndex = -1; // 인덱스 -1 : 존재하지 않는 인덱스의 대명사

        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundIndex = i;
            break;
          }
        }

        if (foundIndex == -1) {
          System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
          continue;
        } else {
          articles.remove(foundIndex);
          System.out.printf("%d번 게시물이 삭제 되었습니다.\n", id);
        }

      } else if (cmd.startsWith("article modify ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = null;

        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundArticle = article;
            break;
          }
        }

        if (foundArticle == null) {
          System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
          continue;
        } else {
          System.out.print("제목 : ");
          String title = sc.nextLine();
          System.out.print("내용 : ");
          String body = sc.nextLine();

          foundArticle.title = title;
          foundArticle.body = body;
          System.out.printf("%d번 게시물이 수정 되었습니다.\n", id);
        }

      } else {
        System.out.println("존재하지 않는 명령어입니다.");
      }
    }

    sc.close();
    System.out.println("== 프로그램 종료 ==");
  }

  private static void makeTestData() {
    System.out.println("게시글 테스트 데이터를 생성합니다.");
    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }
}

class Article {
  int id;
  String regDate;
  String title;
  String body;
  int viewCnt;

  public Article(int id, String regDate, String title, String body) {
    this(id, regDate, title, body, 0);
  }

  public Article(int id, String regDate, String title, String body, int viewCnt) {
    this.id = id;
    this.regDate = regDate;
    this.title = title;
    this.body = body;
    this.viewCnt = viewCnt;
  }

  public void increaseViewCnt() {
    viewCnt++;
    new App().start();
  }
}