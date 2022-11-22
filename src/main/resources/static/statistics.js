const ctx = document.getElementById('lineChart');

let statData,
    statLabels;

$.getJSON('/statistics/line', function(data) {
    new Chart(ctx, {
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