const lineStatistics = document.getElementById('lineChart');
const incomeCircleStatistics = document.getElementById('incomeCircleChart');
const expenseCircleStatistics = document.getElementById('expenseCircleChart');

let statData,
    statLabels;

$.getJSON('/statistics/line', function (data) {
    new Chart(lineStatistics, {
        type: 'line',
        data: {
            labels: data[0].labels,
            datasets: [
                {
                    label: 'Earned',
                    data: data[0].values,
                    borderColor: 'rgb(25, 135, 84)'
                },
                {
                    label: 'Spent',
                    data: data[1].values,
                    borderColor: 'rgb(165, 29, 42)'
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});

$.getJSON('/statistics/circle', function (data) {
    new Chart(incomeCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[0].categories,
            datasets: [
                {
                    label: 'Earned',
                    data: data[0].values,
                    backgroundColor: [
                        'rgb(2, 80, 196)',
                        'rgb(79, 10, 192)',
                        'rgb(165, 33, 99)',
                        'rgb(165, 29, 42)',
                        'rgb(192, 88, 2)',
                        'rgb(20, 108, 67)',
                        'rgb(150, 112, 0)'
                    ],
                },
            ]
        }
    });

    new Chart(expenseCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[1].categories,
            datasets: [
                {
                    label: 'Spent',
                    data: data[1].values,
                    backgroundColor: [
                        'rgb(2, 80, 196)',
                        'rgb(79, 10, 192)',
                        'rgb(165, 33, 99)',
                        'rgb(165, 29, 42)',
                        'rgb(192, 88, 2)',
                        'rgb(20, 108, 67)',
                        'rgb(150, 112, 0)'
                    ],
                },
            ]
        }
    });
});