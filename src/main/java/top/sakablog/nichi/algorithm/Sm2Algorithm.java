package top.sakablog.nichi.algorithm;

public class Sm2Algorithm {

    /**
     * SM-2 算法核心逻辑
     * @param score 用户评分 (0-5):
     * 5: 完美记忆; 4: 犹豫后正确; 3: 费力正确;
     * 2: 错误但想起来了; 1: 错误; 0: 完全不认识
     * @param currentRep 连续成功次数 (reps)
     * @param currentEf 简易度因子 (easiness)
     * @param currentInterval 当前间隔 (interval)
     * @return 更新后的进度对象
     */
    public static ReviewResult calculate(int score, int currentRep, double currentEf, int currentInterval) {
        int nextRep;
        double nextEf;
        int nextInterval;
        int status;

        // 1. 计算简易度因子 EF (Easiness Factor)
        // 公式: EF' = EF + (0.1 - (5-score) * (0.08 + (5-score) * 0.02))
        nextEf = currentEf + (0.1 - (5 - score) * (0.08 + (5 - score) * 0.02));
        if (nextEf < 1.3) nextEf = 1.3; // EF 最小值为 1.3

        // 2. 计算复习间隔 Interval 和 连续成功次数 Reps
        if (score > 3) { // 记忆成功
            if (currentRep == 0) {
                nextInterval = 1;
            } else if (currentRep == 1) {
                nextInterval = 6;
            } else {
                nextInterval = (int) Math.round(currentInterval * nextEf);
            }
            nextRep = currentRep + 1;
        } else { // 记忆失败
            nextRep = 0;
            nextInterval = 1; // 失败后立即重来（通常设为1天内或立即）
        }

        // 3. 判断状态 (业务逻辑：例如连续正确5次以上认为已掌握)
        status = (nextRep >= 5) ? 2 : 1;

        return new ReviewResult(nextRep, nextEf, nextInterval, status);
    }

    // 内部类用于承载计算结果
    public static class ReviewResult {
        public int reps;
        public double ef;
        public int interval;
        public int status;

        public ReviewResult(int reps, double ef, int interval, int status) {
            this.reps = reps;
            this.ef = ef;
            this.interval = interval;
            this.status = status;
        }
    }
}