package club.supreme.framework.web;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    public static void main(String[] args) {

//        log.info("result: {}", pow(15));
        log.info("result: {}", findN(10322422));

    }

    public static int pow(int n){
        int sum = 1;
        int tmp = 3;
        while(n != 0){
            if((n & 1) == 1){
                sum *= tmp;
            }
            tmp *= tmp;
            n = n >> 1;
        }

        return sum;
    }

    public static int findN(int n){
        log.info("2...{}", Integer.MAX_VALUE);
        log.info("1: {}", n >> 1);
        n |= n >> 1;
        log.info("n: {}", n);

        log.info("2: {}", n >> 2);
        n |= n >> 2;
        log.info("n: {}", n);

        log.info("4: {}", n >> 4);
        n |= n >> 4;
        log.info("n: {}", n);

        log.info("8: {}", n >> 8);
        n |= n >> 8; // 整型一般是 32 位，上面我是假设 8 位。
        log.info("n: {}", n);

        log.info("16: {}", n >> 16);
        n |= n >> 16; // 整型一般是 32 位，上面我是假设 8 位。
        log.info("n: {}", n);

        // 无符号右移32位为本身
        log.info("32: {}", n >> 32);
        n |= n >> 32; // 整型一般是 32 位，上面我是假设 8 位。
        log.info("n: {}", n);

        return (n + 1) >> 1;
    }
}
