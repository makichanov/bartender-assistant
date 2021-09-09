<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="controller?command=home">Bartender Assistant</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="controller?command=cocktails">Cocktails</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Bartenders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-success my-2 my-sm-0"
                       href="controller?command=create_cocktail_page">Create
                        your cocktail</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <ul class="navbar-nav d-flex align-items-center">
                <c:choose>
                    <c:when test="${authenticated}">
                        <li class="nav-item">
                            <a class="nav-item nav-link" href="#">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-item nav-link" href="controller?command=logout">Log out</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-item nav-link" href="controller?command=login">Log in</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>
</header>