package test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import webtest.Spittle;
import webtest.controller.SpittleController;
import webtest.data.SpittleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by king on 2017/6/27.
 */
public class ControllerTest {
    @org.junit.Test
    public void shouldShowRecentSpittles()throws Exception{
        List<Spittle> expectedSpittles=createSpittleList(20);
        SpittleRepository mockRepository=mock(SpittleRepository.class);
        when(mockRepository.findSpittles(Long.MAX_VALUE,20)).thenReturn(expectedSpittles);
        SpittleController spittleController=new SpittleController(mockRepository);
        MockMvc mockMvc =standaloneSetup(spittleController)
                .setSingleView (new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList",hasItems(expectedSpittles.toArray())));
    }

    private List<Spittle> createSpittleList(int count){
        List<Spittle> spittles=new ArrayList<Spittle>();
        for(int i=0; i< count; i++){
            spittles.add(new Spittle("Spittle"+i,new Date()));
        }
        return spittles;
    }
}

