<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<html>
<head>
    <title><fmt:message key="cocktails.title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<c:import url="/jsp/header.jsp" charEncoding="utf-8"/>
<div class="d-flex flex-row">
    <form class="form-inline my-2 my-lg-0 mx-3" action="controller" method="post">
        <input type="hidden" name="command" value="search_cocktail_name">
        <input name="cocktail_name" value="${requestScope.cocktail_name}" class="form-control mr-sm-2" type="search"
               placeholder="<fmt:message key="cocktails.search.placeholder"/>" aria-label="<fmt:message key="cocktails.search.placeholder"/>"
        pattern="[a-zA-Zа-яА-ЯЁё1-9. -]*" maxlength="30">
        <button class="btn btn-success my-2 my-sm-0" type="submit"><fmt:message key="cocktails.search.submit"/></button>
    </form>
</div>
<c:if test="${requestScope.cocktail_name != null}">
    <p><small><fmt:message key="cocktails.search.found"/>${requestScope.cocktails.size()}</small></p>
</c:if>
<div class="list-group">
    <c:forEach var="cocktail" items="${requestScope.cocktails}">
        <a href="controller?command=show_cocktail&id=${cocktail.id}"
           class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex container-fluid justify-content-between">
                <div class="d-flex flex-row">
                    <img src="${cocktail.imageSource}" class="img-fluid w-25"
                         alt="">
                    <div class="d-flex flex-column m-2">
                        <h5 class="mb-1">${cocktail.name}</h5>
                        <p class="mb-1 w-25">${fn:substring(cocktail.instructions, 0, 100)}</p>
                    </div>
                </div>
                <small><fmt:formatNumber type="number" maxFractionDigits="2" value="${cocktail.averageMark}"/></small>
            </div>
        </a>
    </c:forEach>
</div>
<c:if test="${requestScope.cocktail_name == null}">
    <ul class="pagination">
        <li class="page-item <c:if test="${requestScope.current_page - 1 <= 0}">disabled</c:if>"><a class="page-link"
                                                                                                    href="controller?command=cocktails&page=${requestScope.current_page - 1}"><fmt:message key="pagination.prev"/></a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="controller?command=cocktails&page=${requestScope.current_page}">${requestScope.current_page}</a>
        </li>
        <li class="page-item <c:if test="${requestScope.is_last_page}">disabled</c:if>"><a class="page-link"
                                                                                           href="controller?command=cocktails&page=${requestScope.current_page + 1}"><fmt:message key="pagination.next"/></a>
        </li>
    </ul>
</c:if>
<c:import url="/jsp/footer.jsp" charEncoding="utf-8"/>
</body>
</html>
