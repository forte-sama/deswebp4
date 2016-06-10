<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="/css/custom.css">
    <meta charset="UTF-8">
    <title>Registro</title>
    <script type="text/javascript" src="/js/jquery-2.2.4.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>
<#include "nav.ftl">
</body>
<div class="container">
    <div class="row">
        <div class="col col-md-6 col-md-push-3">
            <div class="well well-lg">
                <div class="row">
                    <div class="col col-md-12">
                        <#if action == "register">
                        <h1>Formulario de registro</h1>
                        <#elseif action == "edit_user">
                            <h1>Formulario de edicion</h1>
                        </#if>
                    </div>
                </div>
                <#if msg??>
                <div class="row">
                    <div class="col col-md-12 text-danger">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <p>${msg}</p>
                        </div>
                    </div>
                </div>
                </#if>
                <div class="row">
                    <div class="col col-md-12">
                        <form
                            <#if action == "register">
                            action="/user/register"
                            <#elseif action == "edit_user">
                            action="/admin/user/edit"
                            </#if>
                            method="post">
                            <#include "login_register_form_fields.ftl">
                            <div class="row">
                                <div class="col-md-7">
                                    <button type="submit" name="submit" value="submit" class="btn btn-primary btn-lg">
                                        Terminar <#if action == "register">Registro<#elseif action == "edit_user">Edicion</#if>
                                    </button>
                                </div>
                                <#if action == "edit_user" && username??>
                                <div class="col-md-3 col-md-offset-2">
                                    <a class="btn btn-danger" href="/admin/user/delete/${username}">Borrar usuario</a>
                                </div>
                                </#if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</html>