document.addEventListener('DOMContentLoaded', function() {
    fetchHomeworkData();

    function fetchHomeworkData() {
        // 假设你有一个API可以获取作业数据
        // 这里使用静态数据作为示例
        const currentHomework = [
            { id: 1, name: '数学作业', dueDate: '2024-12-01', status: 'current' },
            { id: 2, name: '物理作业', dueDate: '2024-11-05', status: 'current' }
        ];
        const completedHomework = [
            { id: 3, name: '化学作业', dueDate: '2024-11-20', status: 'completed' }
        ];
        const unpublishedHomework = [
            { id: 4, name: '生物作业', dueDate: '2024-12-10', status: 'unpublished' }
        ];

        updateHomeworkList(currentHomework, 'currentHomework');
        updateHomeworkList(completedHomework, 'completedHomework');
        updateHomeworkList(unpublishedHomework, 'unpublishedHomework');
    }

    function updateHomeworkList(homeworkList, listId) {
        const listElement = document.getElementById(listId);
        listElement.innerHTML = ''; // 清空列表
        homeworkList.forEach(homework => {
            const li = document.createElement('li');
            li.textContent = `${homework.name} - 截止日期: ${homework.dueDate}`;

            // 根据作业状态添加不同的类
            switch (homework.status) {
                case 'completed':
                    li.classList.add('completedHomework');
                    break;
                case 'unpublished':
                    li.classList.add('unpublishedHomework');
                    break;
                case 'current':
                    // 检查截止日期是否已过，如果是，则添加 overdueHomework 类
                    if (new Date(homework.dueDate) < new Date()) {
                        li.classList.add('overdueHomework');
                    } else {
                        li.classList.add('currentHomework');
                    }
                    break;
            }

            listElement.appendChild(li);
        });
    }
});