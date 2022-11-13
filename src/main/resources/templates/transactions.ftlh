<#import "parts/common.ftlh" as c>


<@c.page>
    <h4>List of transactions</h4>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Category</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Message</th>
            <th>Transaction Date</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <#list transactions as transaction>
            <tr>
                <td>${transaction.id}</td>
                <td>${transaction.category.category}</td>
                <td>${transaction.isIncome?string("Income", "Expanse")}</td>
                <td>${transaction.amount} MDL</td>
                <td>${transaction.message}</td>
                <td>${transaction.transactionDate}</td>
                <td><a class="btn btn-outline-primary btn-sm" href="/transactions/${transaction.id}" role="button">Edit</a></td>
                <td><a class="btn btn-outline-danger btn-sm" href="/transactions/delete/${transaction.id}" role="button">Delete</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>