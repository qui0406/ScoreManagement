//package com.scm.services.impl;
//
//import com.scm.dto.responses.ScoreStudentResponse;
//import com.scm.pojo.Score;
//import com.scm.repositories.ClassDetailsRepository;
//import com.scm.repositories.ScoreStudentRepository;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.embedded.RedisServer;
//
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ScoreStudentServiceImplTest {
//    private RedisServer redisServer;
//
//    @InjectMocks
//    private ScoreStudentServiceImpl scoreStudentService;
//
//    @Mock
//    private ScoreStudentRepository scoreStudentRepository;
//
//    @Mock
//    private ClassDetailsRepository classDetailsRepository;
//
//    private AnnotationConfigApplicationContext context;
//
//    private CacheManager cacheManager;
//
//    @Before
//    public void setUp() throws Exception {
//        // Khởi chạy embedded Redis
//        redisServer = new RedisServer(6379);
//        redisServer.start();
//
//        // Cấu hình Spring context cho caching
//        context = new AnnotationConfigApplicationContext(TestConfig.class);
//        cacheManager = context.getBean(CacheManager.class);
//
//        // Inject cacheManager vào service
//        scoreStudentService.setCacheManager(cacheManager);
//    }
//
//    @After
//    public void tearDown() {
//        redisServer.stop();
//        context.close();
//    }
//
//    @Test
//    public void testGetScoreByStudentAndClass_Caching() {
//        // Mock data
//        Score mockScore = new Score();
//        when(scoreStudentRepository.getScoresByStudentAndClass("1", "CS101"))
//                .thenReturn(List.of(mockScore));
//
//        // Lần gọi đầu tiên - chưa có cache
//        ScoreStudentResponse firstCall = scoreStudentService.getScoreByStudentAndClass("1", "CS101");
//
//        // Lần gọi thứ hai - lấy từ cache
//        ScoreStudentResponse secondCall = scoreStudentService.getScoreByStudentAndClass("1", "CS101");
//
//        // Kiểm tra repository chỉ được gọi 1 lần
//        verify(scoreStudentRepository, times(1)).getScoresByStudentAndClass("1", "CS101");
//
//        // Kiểm tra cache đã được lưu
//        Cache cache = cacheManager.getCache("studentScores");
//        assertNotNull(cache);
//        assertNotNull(cache.get("1_CS101"));
//    }
//
//    @Configuration
//    @EnableCaching
//    static class TestConfig {
//        @Bean
//        public CacheManager cacheManager() {
//            return new ConcurrentMapCacheManager("studentScores", "classScores", "scorePDFs");
//        }
//    }
//
//    @Test
//    public void testCacheEvictionOnScoreUpdate() {
//        // Setup mock data
//        Score mockScore = new Score();
//        when(scoreStudentRepository.getScoresByStudentAndClass("S001", "CS101"))
//                .thenReturn(Collections.singletonList(mockScore));
//
//        // Populate cache
//        scoreStudentService.getScoreByStudentAndClass("S001", "CS101");
//
//        // Verify cache is populated
//        Cache cache = cacheManager.getCache("studentScores");
//        assertNotNull(cache.get("S001_CS101"));
//
//        // Create score update request
//        ScoreRequest updateRequest = new ScoreRequest();
//        updateRequest.setStudentId("S001");
//        updateRequest.setClassDetailId("CS101");
//        updateRequest.setScore(new BigDecimal("8.5"));
//        updateRequest.setScoreTypeId("1");
//
//        // Mock update operation
//        doNothing().when(scoreService).updateScore(any(), any());
//
//        // Perform update (should evict cache)
//        scoreStudentService.updateScore(updateRequest, "T001");
//
//        // Verify cache was cleared
//        assertNull(cache.get("S001_CS101"));
//    }
//
//    import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//// ...
//
//    @Test
//    public void testRedisIntegration() {
//        // Configure real Redis connection
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setHostName("localhost");
//        connectionFactory.setPort(6379);
//        connectionFactory.afterPropertiesSet();
//
//        // Create RedisTemplate
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.afterPropertiesSet();
//
//        // Test connection
//        redisTemplate.opsForValue().set("testKey", "testValue");
//        assertEquals("testValue", redisTemplate.opsForValue().get("testKey"));
//
//        // Cleanup
//        redisTemplate.delete("testKey");
//    }
//}
