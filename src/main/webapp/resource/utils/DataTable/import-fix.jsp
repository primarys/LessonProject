<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="import.jsp"%>
<style>
    .dataTables_wrapper .sorting:after,
    .dataTables_wrapper .sorting_asc:after,
    .dataTables_wrapper .sorting_desc:after {
        display: none;
    }
    .dataTables_paginate {
        text-align: right;
    }
    .dataTables_paginate a {
        padding: 6px 12px;
        border: 1px solid #ccc;
        margin-left: -1px;
    }
    .dataTables_paginate a.paginate_active {
        background: #49bf67;
        color: #fff;
    }
    .dataTables_wrapper {
        overflow-y: hidden;
        padding-bottom: 10px;
        clear:both;
        width: 100%;
    }
    .dataTables_filter input {
        box-shadow: rgba(0, 0, 0, 0.075) 0px 1px 1px inset;
        border: 1px solid #d0d0d0;
    }
    table{
        width: 100%;
    }
</style>