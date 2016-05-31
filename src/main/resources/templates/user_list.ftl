<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="/css/custom.css">
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery-2.2.4.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>
<#include "nav.ftl">
</body>
<div class="container">
    <div class="row">
        <div class="col col-md-12">
            <div class="well well-lg">
                <div class="row">
                    <div class="col col-md-12">
                        <h1>Lista de Usuarios</h1>
                        <hr />
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-hover">
                            <thead>
                            <th>Username</th>
                            <th>Nombre</th>
                            <th>Tipo de usuario</th>
                            <th>Acciones</th>
                            </thead>
                            <tbody>
                            <#list usuarios as us>
                            <tr>
                                <td>${us.getUsername()}</td>
                                <td>${us.getNombre()}</td>
                                <td>
                                    <#if us.isAdministrador() == true>
                                        administrador
                                    <#elseif us.isAutor() == true>
                                        autor
                                    <#else>
                                        lector
                                    </#if>
                                </td>
                                <td>
                                    <a class="btn btn-warning" href="/admin/user/edit/${us.username}">Editar</a>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</html>