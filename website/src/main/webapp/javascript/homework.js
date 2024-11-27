document.addEventListener('DOMContentLoaded', function() {
    let token = localStorage.getItem("token");
    let name = localStorage.getItem("name");
    if (name === null || token === null) {
        window.location.href = "login.jsp";
    } else {
        document.getElementById("avatar-name").textContent = name
    }
    fetch('api/homework/display', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({token})
    })
        .then(response => response.json())
        .then(data => {
            if (data.error_code === 0) {
                console.log("ok");
                updateHomeworkLists(data);
            } else {
                console.log("failure");
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function updateHomeworkLists(data) {
    // 更新超时作业列表
    updateList('overdueHomework', data.overdueHomework);
    // 更新未发布作业列表
    updateList('unpublishedHomework', data.unpublishedHomework);
    // 更新已完成作业列表
    updateList('completedHomework', data.completedHomework);
    // 更新当前作业列表
    updateList('currentHomework', data.currentHomework);
}

function updateList(listId, homeworkItems) {
    console.log("now updating list" + listId)
    const listElement = document.getElementById(listId);
    listElement.innerHTML = ''; // 清空现有列表
    homeworkItems.forEach(item => {
        console.log("now updating list" + listId + item.content)
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <div class="homework-item">
                <h3>${item.content}</h3>
                <p>截止时间: ${formatDate(item.endTime)}</p>
                <p>分数: ${item.score}</p>
                <p>${item.completed ? '已完成' : '未完成'}</p>
            </div>
        `;
        listElement.appendChild(listItem);
    });
}

function formatDate(dateStr) {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    return new Date(dateStr).toLocaleDateString('zh-CN', options);
}