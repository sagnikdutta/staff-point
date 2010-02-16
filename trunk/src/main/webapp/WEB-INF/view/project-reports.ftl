<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="project" type="ru.point.model.Project" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${project.name}</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">�������</a> &rarr;
            <@projectRef project=project />
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box one">
    <div id="boxheader">
        <h1 class="top">${project.name}<span class="tip red"></span></h1>
        <span class="position">� ������� �������� ${project.activities?size} �������</span>

        <div id="subnav">
            <ul class="nav">
                <li><a href="/project/${project.id}">����</a><a class="arrow toggle" rel="sorting" href="#"
                                                                onclick="return false;"><img
                        src="/i/menu_arrow.png" alt=""></a>

                    <div id="sorting" class="menu hidden">
                        <h3>����������� ��:</h3>
                        <ul>
                            <li><a href="/project/${project.id}/by/name">��������</a></li>
                            <li><a href="/project/${project.id}/by/role">����</a></li>
                            <li><a href="/project/${project.id}/by/birthday">���� ��������</a></li>
                        </ul>
                    </div>
                </li>
                <li><a class="selected" href="/project/report/${project.id}">����������</a></li>
                <li><a href="/project/edit/${project.id}">�������������</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top"><span class="tip red"></span></h1>

    <ul>
        <#list project.activities as position>
        <li class="position"><@userRef user=position.user/> &mdash; ${position.role.name}<br/>
            <#if position.lastReport?? >
            <ul class="report">
                <li><@period report=position.lastReport />
                    ${position.lastReport.text?html?replace("\n", "<br/>")}</li>
            </ul>
            </#if>
        </li>
        </#list>
    </ul>

    <h1 class="top">�������<span class="tip red"></span></h1>

    <div class="file pdf ">
        <a href="/"><img src="/i/0.gif"/>������������� ��������� PDF �����</a>
        <span class="size">pdf, application/pdf</span>
    </div>

    <div class="file pdf ">
        <a href="/"><img src="/i/0.gif"/>������������� �������� PDF �����</a>
        <span class="size">pdf, application/pdf</span>
    </div>

    <div class="file pdf ">
        <a href="/"><img src="/i/0.gif"/>������������� PDF ����� �� ��������� �����</a>
        <span class="size">pdf, application/pdf</span>
    </div>

    <div class="file xls ">
        <a href="/"><img src="/i/0.gif"/>������� ������� Excel �� ��������� �����</a>
        <span class="size">xls, application/excel</span>
    </div>

</div>
<@foot/>
</body>
</html>
