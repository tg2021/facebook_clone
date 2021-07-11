const modalElem = document.querySelector('section .modal');
const modalCloseElem = document.querySelector('section .modal .modal_close');
const btnFollowElem = document.querySelector('#btnFollow'); //팔로우 버튼
const profileImgElem = document.querySelector('#profileImg');

//모달창 띄우기 이벤트
profileImgElem.addEventListener('click', () => {
    modalElem.classList.remove('hide');
});

//모든 no-main-profile 아이콘에 이벤트를 걸어준다.
//이벤트는 메인 이미지 변경처리
const profileImgParentList = document.querySelectorAll('.profile-img-parent');
profileImgParentList.forEach(item => {
    const iElem = item.querySelector('i');
    if(iElem != null) {
        addIElemEvent(iElem);
    }
});
//메인이미지 바꾸기 아이콘에 이벤트 설정
function addIElemEvent(target) {
    target.addEventListener('click', () => {
        const iprofile = target.parentNode.dataset.iprofile;
        console.log(iprofile);
        changeMainProfile(iprofile);
    });
}
//메인 이미지 변경
function changeMainProfile(iprofile) {
    fetch(`/user/mainProfile?iprofile=${iprofile}`)
        .then(res => res.json())
        .then(myJson => {
            switch(myJson.result) {
                case 0:
                    alert('메인 이미지 변경에 실패하였습니다.'); break;
                case 1:
                    setMainProfileIcon(iprofile);
                    //section에 있는 프로필 이미지 변경
                    const src = profileImgElem.src;
                    const frontSrc = src.substring(0, src.lastIndexOf("/"));
                    const resultSrc = `${frontSrc}/${myJson.img}`
                    profileImgElem.src = resultSrc;
                    //헤더에 있는 프로필 이미지 변경
                    const headerProfileImgElem = document.querySelector('header .span__profile img');
                    headerProfileImgElem.src = resultSrc;
                    break;
            }
        });
}
//현재 mainProfile로 바뀐 iprofile값 입니다.
function setMainProfileIcon(iprofile) {
    profileImgParentList.forEach(item => {
        item.innerHTML = '';
        const itemIprofile = item.dataset.iprofile;
        if(iprofile !== itemIprofile) {
            const iElem = document.createElement('i');
            iElem.className = 'no-main-profile pointer fas fa-bell';
            item.append(iElem);
            addIElemEvent(iElem);
        }
    });
}
//모달창 띄우기
if(profileImgElem) {
    profileImgElem.addEventListener('click', () => {
        modalElem.classList.remove('hide');
    });
}
//모달창 닫기
if(modalCloseElem) {
    modalCloseElem.addEventListener('click', () => {
        modalElem.classList.add('hide');
        //location.reload();
    });
}
if(btnFollowElem) {
    btnFollowElem.addEventListener('click', () => {
        const param = { iuserYou: localConstElem.dataset.iuser };
        const init = {}
        let queryString = '';
        switch(btnFollowElem.dataset.follow) {
            case '0': //no팔로우 > 팔로우
                init.method = 'POST';
                init.headers = { 'Content-Type': 'application/json' };
                init.body = JSON.stringify(param);
                break;
            case '1': //팔로우 > 팔로우취소
                init.method = 'DELETE';
                queryString = `?iuserYou=${param.iuserYou}`;
                break;
        }
        fetch('follow' + queryString, init)
            .then(res => res.json())
            .then(myJson => {
                console.log(myJson);
                if(myJson.result === 1) {
                    let buttnNm = '팔로우 취소';
                    if(btnFollowElem.dataset.follow === '1') {
                        if(myJson.youFollowMe == null) { buttnNm = '팔로우'; }
                        else { buttnNm = '맞팔로우'; }
                    }
                    btnFollowElem.classList.toggle('instaBtnEnable');
                    btnFollowElem.value = buttnNm;
                    btnFollowElem.dataset.follow = 1 - btnFollowElem.dataset.follow;
                } else {
                    alert('에러가 발생하였습니다.');
                }
            });
    });
}

const localConstElem = document.querySelector('#localConst');
feedObj.url = '/user/feedList';
feedObj.setScrollInfinity(window);
feedObj.iuser = localConstElem.dataset.iuser;
feedObj.getFeedList(1);