import process from 'process';

let promise = Promise.reject(new Error("프로미스 실패!"));
setTimeout(()=> promise.catch(err=>console.log("처리됨")),1000);
    
process.on('unhandledRejection', (reason, promise) => {    
    console.log('Unhandled Rejection at:', promise, 'reason:', reason);
});