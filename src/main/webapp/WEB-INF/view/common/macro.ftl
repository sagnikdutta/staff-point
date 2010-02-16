<#macro inc>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<link type="text/css" rel="stylesheet" href="/css/reset.css"/>
<link type="text/css" rel="stylesheet" href="/css/main.css"/>
<link type="text/css" rel="stylesheet" href="/css/jquery.autocomplete.css"/>
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.form-2.24.js"></script>
<script type="text/javascript" src="/js/jquery.autocomplete.js"></script>
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
    <form action="/search" id="searchform" method="get">
        <input id="query" class="cleanOnFocus" type="text" value="искать" name="query"/>
        <input type="image" id="searchsubmit" src="/i/magnify.gif"/>
    </form>
</div>
</#macro>

<#macro inout session="">
<ul class="right">
    <#if session?has_content>
    <li><@userRef user=session.user/></li>
    <li><a href="/logout">Выйти</a></li>
    <#else>
    <li><a href="/welcome">Кто ты?</a></li>
    </#if>
</ul>
</#macro>

<#macro userRef user><a class="user" href="/user/${user.id}">${user.fullName!"null"}</a></#macro>

<#macro projectRef project>
<a href="/project/${project.id}">${project.name}</a>
</#macro>

<#macro projectTree projects>
<#if projects?? && projects?has_content>
<ul>
    <#list projects as project>
    <li>
        <@projectRef project=project /><span class="qty">${project.activities?size}</span>
        <@projectTree projects=project.children/>
    </li>
    </#list>
</ul>
</#if>
</#macro>

<#macro activitiesTwoColumns activities groupby="">
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

<#macro peopleTwoColumns users groupby="">
<div class="people">
    <#list users as user>
    <div class="person">
        <span><a href="/user/${user.id}"><img src="/user/image/s/${user.id}"/></a></span>
        <h4><@userRef user=user/></h4>

        <p class="picture-note">
            <#if groupby?has_content>
            <#if groupby == "birthday">
            ${user.profile.birthDay.time?date}
            </#if>
            <#else>
            <#--${user.mainActivity.role.name}-->
            </#if>
        </p>
    </div>
    </#list>
</div>
</#macro>

<#macro period report>
<h3>${report.start.time?date} ~ ${report.end.time?date}:
    <span <#if report.old>class="old"</#if>>${report.passed}</span></h3>
</#macro>

        <#assign gen = "ru.point.utils.russian.DeclensionFreeMarkerDirective"?new(0)/>