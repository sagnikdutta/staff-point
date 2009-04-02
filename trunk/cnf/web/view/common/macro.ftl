<#macro inc>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<link type="text/css" rel="stylesheet" href="/css/reset.css"/>
<link type="text/css" rel="stylesheet" href="/css/main.css"/>
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/qms.js"></script>
</#macro>

<#macro head>
<head>
    <title><#nested /> - Point</title>
    <@inc />
</head>
</#macro>

<#macro foot>

</#macro>

<#macro search>
<div id="search" class="form">
    <form action="/search" method="get">
        <input class="cleanOnFocus" type="text" value="искать" name="query">
    </form>
</div>
</#macro>

<#macro inout session="">
<#if session?has_content>
<@logout session/>
<#else>
<@login />
</#if>
</#macro>

<#macro login>
<label><a id="searchToggle" href="#">Войти</a></label>
</#macro>

<#macro loginForm session="">
<#if !session?has_content>
<div id="login" class="form hidden">
    <form action="/login" method="post">
        <input class="cleanOnFocus" type="text" value="логин" name="login">&nbsp;
        <input class="cleanOnFocus" type="password" value="пароль" name="password">&nbsp;
        <input type="submit" value="&rarr;" name="go">
    </form>
</div>
</#if>
</#macro>

<#macro logout session>
<label><@userRef user=session.user/>&nbsp;|&nbsp;<a href="/logout">Выйти</a></label>
</#macro>

<#macro userRef user><a class="user" href="/user/${user.id}">${user.fullName}</a></#macro>

<#macro projectRef project>
<a href="/project/${project.id}">${project.name}</a>
</#macro>

<#macro projectTree projects>
<#if projects?? && projects?has_content>
<ul>
    <#list projects as project>
    <li>
        <@projectRef project=project />
        <@projectTree projects=project.children/>
    </li>
    </#list>
</ul>
</#if>
</#macro>

<#macro peopleTwoColumns activities groupby="">
<div class="people">
    <#list activities as activity>
    <div class="person">
        <span><a href="/user/${activity.user.id}"><img src="/user/image/s/${activity.user.id}"/></a></span>
        <h4><@userRef user=activity.user/></h4>

        <p class="picture-note">
            <#if groupby?has_content>
            <#if groupby == "birthday">
            ${activity.user.profile.birthDay.time?date}
            </#if>
            <#else>
            ${activity.role.name}
            </#if>
        </p>
    </div>
    </#list>
</div>
</#macro>

        <#assign gen = "ru.point.utils.russian.DeclensionFreeMarkerDirective"?new(0)/>