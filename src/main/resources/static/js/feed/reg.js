const fileList = [];
const ctntElem = document.querySelector('#ctnt');
const locationElem = document.querySelector('#location');
const selectImgArrElem = document.querySelector('#selectImgArr');
const btnUploadElem = document.querySelector('#btnUpload');
const displayImgListElem = document.querySelector('#displayImgList');

// 글내용 변경시
// keydown : 키가 눌렸을 때, keyup : 키 누름이 해제될 때
ctntElem.addEventListener('keyup', () => {
   toggleBtnUpload(); 
});

//이미지들이 선택되면 fileList에 추가하기
selectImgArrElem.addEventListener('change', () => {
    const files = selectImgArrElem.files;
    for(let i=0; i<files.length; i++) {
        fileList.push(files[i]);
    }
    displaySelectedImgArr();

});

//fileList에 추가 된 이미지들 디스플레이하기
function displaySelectedImgArr() {
    displayImgListElem.innerHTML = '';
    toggleBtnUpload();
    for(let i = 0; i < fileList.length; i++) {
        const item = fileList[i];

        const reader = new FileReader();
        reader.readAsDataURL(item);

        //로드 한 후
        reader.onload = () => {
            const img = document.createElement('img');
            img.addEventListener('click', () => {
                fileList.splice(i, 1);
                displaySelectedImgArr();
            });
            img.src = reader.result;
            displayImgListElem.append(img);
        };
    }
}

// 등록버튼 활성화/비활성화 결정
function toggleBtnUpload() {
    btnUploadElem.disabled = true; // true일때 비활성화 false일때 활성화
    if(ctntElem.value.length > 0 || fileList.length > 0) {
        btnUploadElem.disabled = false;
    }
}

//등록버튼 클릭시 (Ajax로 파일 업로드)
btnUploadElem.addEventListener('click', () => {
    const data = new FormData();
    // 무언가 적혀있으면 내용추가
    if(ctntElem.value.length > 0) { data.append(ctntElem.id, ctntElem.value); }
    if(locationElem.value.length > 0) { data.append(locationElem.id, locationElem.value); }
    if(fileList.length > 0) {
        for(let i = 0; i < fileList.length; i++) {
            data.append('imgArr', fileList[i]);
        }
    }

    fetch('/feed/reg', {
        method: 'POST',
        body: data
    }).then(res => res.json())
        .then(myJson => {
            switch (myJson.result) {
                case 0:
                    alert('피드 등록에 실패하였습니다.')
                    break;
                case 1:
                    location.href = '/feed/home';
                    break;
            }
        });
})