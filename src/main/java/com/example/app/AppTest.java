import javafx.scene.text.Text;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Timer;

import static org.mockito.Mockito.*;

public class AppTest {

    @Mock
    private Text mockText;

    @Mock
    private Timer mockTimer;

    private YourClass yourClassUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        yourClassUnderTest = new YourClass(mockTimer);
    }

    @Test
    public void testCountdown() {
        // Given
        int countdownSeconds = 5;
        when(mockTimer.schedule(any(), eq(0L), eq(1000L))).thenAnswer(invocation -> {
            TimerTask task = invocation.getArgument(0);
            task.run(); // Simulate the task being executed immediately
            return null;
        });

        // When
        yourClassUnderTest.countdown(mockText);

        // Then
        verify(mockText, times(countdownSeconds)).setText(anyString());
    }

    @Mock
    private Satellite mockSatellite;

    private App AppTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        yourClassUnderTest = new YourClass();
    }

    @Test
    public void testSatAPICall() throws URISyntaxException, IOException, InterruptedException {
        // Given
        Integer satelliteId = 123;
        Float expectedLongitude = 45.678f;
        Float expectedLatitude = -78.901f;

        when(mockSatellite.satlong).thenReturn(expectedLongitude);
        when(mockSatellite.satlat).thenReturn(expectedLatitude);

        // When
        Float[] result = yourClassUnderTest.satAPICall(satelliteId);

        // Then
        verify(mockSatellite, times(1)).setObserverLocation(anyDouble(), anyDouble(), anyDouble());
        verify(mockSatellite, times(1)).updateInfo();
        assertArrayEquals(new Float[]{expectedLongitude, expectedLatitude}, result);
    }

}
