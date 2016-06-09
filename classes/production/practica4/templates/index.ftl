<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="/css/custom.css">
    <meta charset="UTF-8">
    <title>Inicio</title>
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
                <#list articulos as articulo>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        ${articulo.getTitulo()}
                    </div>
                    <div class="panel-body">
                        ${articulo.preview()}
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-md-5">
                            <#list articulo.etiquetas() as etiqueta>
                            <span class="label label-danger">${etiqueta}</span>
                            </#list>
                            </div>
                            <div class="col-md-4 text-warning" style="text-align: right;">
                                <a href="article/view/${articulo.getId()}" class="btn btn-success">Ver mas</a>
                            </div>
                            <div class="col-md-3 text-warning" style="text-align: right;">
                                <p>Escrita  en <em>${articulo.getFecha()}</em> por: <strong>${articulo.getAutorId()}</strong></p>
                            </div>
                        </div>
                    </div>
                </div>
                </#list>
            </div>
        </div>
    </div>
</div>
</html>