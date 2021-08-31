import process from 'process';

Promise.reject(new Error("프로미스 실패!"))
    .catch(err=> console.log(err)); 

// 에러가 잘 처리되어 실행되지 않음.
process.on('unhandledRejection', (reason, promise) => {    
    console.log('Unhandled Rejection at:', promise, 'reason:', reason);
});