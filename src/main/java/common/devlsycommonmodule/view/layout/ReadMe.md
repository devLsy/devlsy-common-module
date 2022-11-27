# thymeleaf layout을 이용한 화면 레이아웃 설정
## template은 bootstrap 5.2(https://getbootstrap.com/docs/5.2/getting-started/download/)

[layout.html]: 공통으로 사용되는 layout
정적 리소스 구조는 아래와 같음 <br>
![image](https://user-images.githubusercontent.com/44331989/204127124-b2493cfa-e294-4436-8ba8-8142083fe8f9.png) <br>
```html
<!doctype html>
<html lang="ko" xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>thymeleaf layout test1</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/dashboard/">
    <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .b-example-divider {
            height: 3rem;
            background-color: rgba(0, 0, 0, .1);
            border: solid rgba(0, 0, 0, .15);
            border-width: 1px 0;
            box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
        }

        .b-example-vr {
            flex-shrink: 0;
            width: 1.5rem;
            height: 100vh;
        }

        .bi {
            vertical-align: -.125em;
            fill: currentColor;
        }

        .nav-scroller {
            position: relative;
            z-index: 2;
            height: 2.75rem;
            overflow-y: hidden;
        }

        .nav-scroller .nav {
            display: flex;
            flex-wrap: nowrap;
            padding-bottom: 1rem;
            margin-top: -1px;
            overflow-x: auto;
            text-align: center;
            white-space: nowrap;
            -webkit-overflow-scrolling: touch;
        }
    </style>

    <!-- Custom styles for this template -->
    <link href="/css/dashboard.css" rel="stylesheet">
</head>
<body>

<!-- header start -->
<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6" href="#">devLsy admin page</a>
    <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <input class="form-control form-control-dark w-100 rounded-0 border-0" type="text" placeholder="Search" aria-label="Search">
    <div class="navbar-nav">
        <div class="nav-item text-nowrap">
            <a class="nav-link px-3" href="#">Sign out</a>
        </div>
    </div>
</header>
<!-- End of header-->

<!-- start container-fluid -->
<div class="container-fluid">
    <!-- start row -->
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3 sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/">
                            <span data-feather="home" class="align-text-bottom"></span>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" th:href="@{/board/list}">
                            <span data-feather="list" class="align-text-bottom"></span>
                            게시판관리
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" th:href="@{/users/list}">
                            <span data-feather="users" class="align-text-bottom"></span>
                            사용자관리
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
        <!-- 기본 템플릿 안에 삽입될 내용 Start -->
        <th:block layout:fragment="content"></th:block>
        <!-- 기본 템플릿 안에 삽입될 내용 End -->

    </div>
    <!-- End of row -->
    <script src="/js/bootstrap/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>
    <script src="/js/dashboard.js"></script>
</div>
<!-- End of container-fluid-->

</body>
</html>
```

[사용예시1: index.html]
```html
<!-- 공통 레이아웃 시작 -->
<html layout:decorate="~{layout}">
<!-- 공통 레이아웃 종료 -->
<!-- start container-->

<!-- layout안의 content에 들어갈 content 영역 start -->  
<div layout:fragment="content" class="container">
    <!-- main -->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">Dashboard</h1>
            <div class="btn-toolbar mb-2 mb-md-0">
                <div class="btn-group me-2">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                </div>
                <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                    <span data-feather="calendar" class="align-text-bottom"></span>
                    This week
                </button>
            </div>
        </div>

        <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas>

        <h2>Section title</h2>
        <div class="table-responsive">
            <table class="table table-striped table-sm">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Header</th>
                    <th scope="col">Header</th>
                    <th scope="col">Header</th>
                    <th scope="col">Header</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1,001</td>
                    <td>random</td>
                    <td>data</td>
                    <td>placeholder</td>
                    <td>text</td>
                </tr>
                <tr>
                    <td>1,002</td>
                    <td>placeholder</td>
                    <td>irrelevant</td>
                    <td>visual</td>
                    <td>layout</td>
                </tr>
                <tr>
                    <td>1,003</td>
                    <td>data</td>
                    <td>rich</td>
                    <td>dashboard</td>
                    <td>tabular</td>
                </tr>
                <tr>
                    <td>1,003</td>
                    <td>information</td>
                    <td>placeholder</td>
                    <td>illustrative</td>
                    <td>data</td>
                </tr>
                <tr>
                    <td>1,004</td>
                    <td>text</td>
                    <td>random</td>
                    <td>layout</td>
                    <td>dashboard</td>
                </tr>
                <tr>
                    <td>1,005</td>
                    <td>dashboard</td>
                    <td>irrelevant</td>
                    <td>text</td>
                    <td>placeholder</td>
                </tr>
                <tr>
                    <td>1,006</td>
                    <td>dashboard</td>
                    <td>illustrative</td>
                    <td>rich</td>
                    <td>data</td>
                </tr>
                <tr>
                    <td>1,007</td>
                    <td>placeholder</td>
                    <td>tabular</td>
                    <td>information</td>
                    <td>irrelevant</td>
                </tr>
                <tr>
                    <td>1,008</td>
                    <td>random</td>
                    <td>data</td>
                    <td>placeholder</td>
                    <td>text</td>
                </tr>
                <tr>
                    <td>1,009</td>
                    <td>placeholder</td>
                    <td>irrelevant</td>
                    <td>visual</td>
                    <td>layout</td>
                </tr>
                <tr>
                    <td>1,010</td>
                    <td>data</td>
                    <td>rich</td>
                    <td>dashboard</td>
                    <td>tabular</td>
                </tr>
                <tr>
                    <td>1,011</td>
                    <td>information</td>
                    <td>placeholder</td>
                    <td>illustrative</td>
                    <td>data</td>
                </tr>
                <tr>
                    <td>1,012</td>
                    <td>text</td>
                    <td>placeholder</td>
                    <td>layout</td>
                    <td>dashboard</td>
                </tr>
                <tr>
                    <td>1,013</td>
                    <td>dashboard</td>
                    <td>irrelevant</td>
                    <td>text</td>
                    <td>visual</td>
                </tr>
                <tr>
                    <td>1,014</td>
                    <td>dashboard</td>
                    <td>illustrative</td>
                    <td>rich</td>
                    <td>data</td>
                </tr>
                <tr>
                    <td>1,015</td>
                    <td>random</td>
                    <td>tabular</td>
                    <td>information</td>
                    <td>text</td>
                </tr>
                </tbody>
            </table>
        </div>
    </main>
    <!-- End of main  -->
</div>
<!-- End of container -->
<!-- layout안의 content에 들어갈 content 영역 종료 -->  
</html>
```

[사용예시2: board_list.html]
```html
<html layout:decorate="~{layout}">
<!-- layout:fragment="content"을 설정한 영역이 layout.html의 content에 삽입된다. -->   
<div layout:fragment="content" class="container">
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <div class="container my-3">
            <table class="table">
                <thead class="table-dark">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>내용</th>
                    <th>작성자</th>
                    <th>등록일</th>
                </tr>
                </thead>
                <tbody>
                    <tr th:each="list : ${boardList}">
                        <td th:text="${list.boardSeq}"></td>
                        <td th:text="${list.title}"></td>
                        <td th:text="${list.contents}"></td>
                        <td th:text="${list.name}"></td>
                        <td th:text="${list.regDate}"></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 게시판 하단 페이지네이션 영역 start -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <!-- prev -->
                <li class="page-item" th:if="${pageMaker.prev} == true">
                    <a class="page-link" th:href="@{/board/list(pageNum=${pageMaker.startPage}-1)}">Prev</a>
                </li>
                <!-- pageMaker의 startPage부터 endPage까지 루프, a태그의 href에 idx를 링크(get방식으로 pageNum을 붙여서) -->
                <li class="page-item" id="paginate_btn" th:each="idx: ${#numbers.sequence(pageMaker.startPage, pageMaker.endPage)}" th:classappend="${pageMaker.cri.pageNum} == ${idx} ? active : null">
                    <a class="page-link" th:href="@{/board/list(pageNum=${idx})}" th:text="${idx}"></a>
                </li>
                <!-- next -->
                <li class="page-item" th:if="${pageMaker.next} == true and ${pageMaker.endPage > 0}">
                    <a class="page-link" th:href="@{/board/list(pageNum=${pageMaker.endPage}+1)}">Next</a>
                </li>
            </ul>
        </nav>
        <!-- // 게시판 하단의 페이지네이션 영역 end -->
    </main>
</div>
</html>
```
