<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${user.fullName}</@head>
<body>
<div class="header">
        <span>
            <a href="/">�������</a> &rarr;
            <@projectRef project=user.mainActivity.project /> &rarr;
            <@userRef user=user />
        </span>
    <@search/>
    <@inout session=session/>
</div>
<div class="box lefty">

    <h1 class="top"><#if session?? && user.id = session.user.id>�, </#if>${user.fullName}
        <span class="tip red"></span></h1>
        <span class="position">${user.mainActivity.name} �
            <@projectRef project=user.mainActivity.project/> &rarr;
            <a class="activity" href="/report/activity/${user.mainActivity.id}">����������</a></span>

    <h1>����������<span class="tip blue"></span></h1>
    <table class="contacts">
        <tr>
            <th>���� ��������:</th>
            <td>${user.profile.birthDay.time?date?string.long}<span> (�� ���� ��� 292 ���)</span></td>
        </tr>
        <tr>
            <th>E-Mail:</th>
            <td>${user.profile.contacts["e-mail"]}</td>
        </tr>
    </table>

    <table id="contacts" class="contacts hidden">
        <#list user.profile.contacts?keys as key >
        <tr>
            <th>${key}:</th>
            <td>${user.profile.contacts[key]}</td>
        </tr>
        </#list>
    </table>

    <a class="toggle more" rel="contacts" href="#">��� ��������&nbsp;&darr;</a>

    <img src="/i/user-128.png"/>

    <h1>� ��� ��������<span class="tip green"></span></h1>


    <#list user.activities as activity>
    <#if activity.reportTo?has_content>
    <p>
        � ������� <@projectRef project=activity.project/> �������� ��� �������
        <@userRef user=activity.reportTo.user/>
    </p>
    </#if>

    <#if activity.reportFrom?has_content >
    <p>
        <strong>� ������� ${user.profile.firstName} ��������:</strong>
    </p>

    <ul class="">
        <#list activity.reportFrom as reporter>
        <li><@userRef user=reporter.user/>, <em>${reporter.name}</em></li>
        </#list>
    </ul>
    </#if>
    </#list>

</div>
<@foot/>
</body>
</html>
