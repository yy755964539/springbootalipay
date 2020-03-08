package com.alipay.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016093000630920";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQSufk2Lik7/n4M3lr9xTloVEdkF1xXclfY9Xvo5X2ajKRqWktuaLoDQmRDBIwGMzgrjQZ34jg88dKKMfeXEwzLxoEqras/AuUERbn8WRtAsZXAURc/NGEInE+/TeYBc4+r3rgCxureOMys21dFxGAwLHKp8WFQ9R9L5Jh2dzmejpi9jRTKZm5YixQ6iwTYODorxnuoKzX5suyx/8r8MMFjHm5z3hjAvpbJQjOp0wpamXVzuzIGWX4Nq1U35AAA67vYvhLn2+zG/8WRBb7XRnoUlFYbSajgZ95MdqjJTDXm5i7A80WOh+iNsDct8PHSECFX135AWxKBUKjVfo5iqQjAgMBAAECggEAapzeAGnc7AlgEfK+iXZTm2N5ErYDROs+CdwzS0/8Usieup+I1j8uTfSsF97qZV09q9krJ97RPR0CKBo/q11Y9AyeVcppFSFZ0LpbyV7DjAQb1ldaUrZ+e/oS4K6p8AqCHDD+QiIS/LxiaqjGapzL0u0NsKYLvG6kxyZqKyCVJjkO9tOE58m3wmHH7mcOts8DR8eBS3+Jr4DwCR92YFwwef0cZlS/5eVfroaAOZIufmAoXz5oDWiDXkybVcjkv6UcKG1sZqy3ABa4qXz9rWaOJt3eRZIQhyrjZfX00iAmltP9anhYpnQuXiuHswZdxG7ZqKU+0QKgaCBqWmwdw4r9qQKBgQD1ZfbBu2B8w8MRb8vCYnPneaiXQSFQGdZjniWhEFImBEsONwWLI6stpgrNF8uA1mqzJrZz1sSXlNEYmKv/CkoepNo4yEv1wfz9sQTWwkhoozIsos8KgN5RI8jeO0fmo8yNU+/+xBxIFg8gMU5VLG+je7FH//QXz/Ao0jNyh2yLBQKBgQCWhr3MWtudJC3TBARDlcDVuQvYKhNCCfO2afm707IbPEzSSGlpJkGEq7kLBz3J+nq7gAi/gtnoDRXmsC2cTWgANBFWWpX4LKwm+lRtvrvYhvNhOtIVVzJPNkemHs6a00kelJ0Ed/nQUu0kHGey+AQ2dN0tfgLm/rj0pY3D8KUrBwKBgC8igp77irDsOQ+kVZLkrjaL26IR1IA6Nj+0JK4nIGBE+tWkI+Bx3XNLLU/vfhslFCGNEgZ1/hTPdMowqPi31TzFlVTKxCxUMm/YDIHjl1zh28VPEE2IKyuSoAZvhIP8elG7CaH5sST3d7hLQCyD+H2CSK73rr7UJeDxXwhkkqixAoGAObZJeNtxDiu74gbNN7hn6cl78BIZth0ossyo8km1JNoWvh0DRUxknGBWnxLUrub5PYBWWk7qCx1RQ1RS+HV5JXLRHuG2VCOUkZEy46zYSqlbEPFDcOkwocw25kszfYr6ot3p4be6MRRKVakKXUTYIKTn1DRXGoeZFDuRQgm22esCgYBBmBhstncYIJ3EIRwKAW9BuVyPQ6g9jFUqzS1lzqs2LQgjYnh5Zn9z+k1hSPW+gHW9LVph8uGtG02/6ACvVwT4sblmYhCPoOgD+KN4vIGwLgFWKn4VyQdF0BfLzLP9saIbtXu8gxLUkr0YILtAc3dHCxcavFFW9SiVLoUFSsVSCQ==";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://119.27.161.118:80/502";//失败页面
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://119.27.161.118:80/200";//成功页面
	// 请求网关地址
	//https://openapi.alipay.com/gateway.do正式环境
	//https://openapi.alipaydev.com/gateway.do沙箱环境
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";

}
