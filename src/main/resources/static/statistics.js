const lineStatistics = document.getElementById('lineChart').getContext("2d");
const incomeCircleStatistics = document.getElementById('incomeCircleChart');
const expenseCircleStatistics = document.getElementById('expenseCircleChart');

/*** Gradient ***/
var greenGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
greenGradient.addColorStop(0, 'rgba(25, 135, 84,1)');
greenGradient.addColorStop(1, 'rgba(25, 135, 84,0)');

var redGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
redGradient.addColorStop(0, 'rgba(165, 29, 42,1)');
redGradient.addColorStop(1, 'rgba(165, 29, 42,0)');

Chart.defaults.font.family = document.getElementById('statTitle').style.fontFamily;

$.getJSON('/statistics/line', function (data) {
    new Chart(lineStatistics, {
        type: 'line',
        data: {
            labels: data[0].labels,
            datasets: [
                {
                    backgroundColor: greenGradient,
                    fill: true,
                    label: 'Earned',
                    data: data[0].values,
                    borderColor: 'rgb(25, 135, 84)',
                    pointBorderColor: 'rgb(25, 135, 84)',
                    pointBackgroundColor: 'rgb(25, 135, 84)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                },
                {
                    backgroundColor: redGradient,
                    fill: true,
                    label: 'Spent',
                    data: data[1].values,
                    borderColor: 'rgb(165, 29, 42)',
                    pointBorderColor: 'rgb(165, 29, 42)',
                    pointBackgroundColor: 'rgb(165, 29, 42)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                }
            ]
        },
        options: {
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += new Intl.NumberFormat('en-US', {
                                    style: 'currency',
                                    currency: 'MDL'
                                }).format(context.parsed.y);
                            }
                            return label;
                        }
                    }
                }
            },
            layout: {
                padding: 10
            },
            responsive: true,
            scales: {
                x: {
                    grid: {
                        display: false
                    }
                },
                y: {
                    grid: {
                        display: true,
                    }
                }

            }
        },
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
                        'rgb(0,255,127)',
                        'rgb(46,139,87)',
                        'rgb(143,188,143)',
                        'rgb(0,250,154)',
                        'rgb(50,205,50)',
                        'rgb(20, 108, 67)',
                        'rgb(128,128,0)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
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
                        'rgb(128, 0, 32)',
                        'rgb(170, 74, 68)',
                        'rgb(210, 4, 45)',
                        'rgb(165, 29, 42)',
                        'rgb(227, 66, 52)',
                        'rgb(129, 65, 65)',
                        'rgb(119, 7, 55)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
                },
            ]
        }
    });
});