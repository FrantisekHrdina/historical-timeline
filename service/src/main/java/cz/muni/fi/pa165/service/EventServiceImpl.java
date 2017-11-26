package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author František Hrdina
 */
@Service
public class EventServiceImpl implements EventService{

    //@Autowired
    @Inject
    private EventDao eventDao;

    @Override
    public void addEvent(Event event) {
        if (event == null){
            throw new DAOException("Error when adding Event, event is null");
        }
        try{
            eventDao.addEvent(event);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when adding Event:{0}", event.toString()), e);
        }

    }

    @Override
    public void removeEvent(Event event) {
        if (event == null){
            throw new DAOException("Error when removing Event, event is null");
        }
        try{
            eventDao.removeEvent(event);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when removing Event:{0}", event.toString()), e);
        }
    }

    @Override
    public void editEvent(Event event) {
        if (event == null){
            throw new DAOException("Error when editing Event, event is null");
        }
        try{
            eventDao.editEvent(event);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when editing Event:{0}", event.toString()), e);
        }
    }

    @Override
    public Event findEvent(Long id) {
        try{
            return eventDao.findEvent(id);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when editing Event id:{0}", id), e);
        }

    }

    @Override
    public List<Event> findAllEvents() {
        try{
            return eventDao.findAllEvents();
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when getting all Events"), e);
        }

    }

    @Override
    public List<Event> findEventsInRange(LocalDate from, LocalDate to) {
        try{
            return eventDao.findEventsInRange(from, to);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when getting all Events in range {0} - {1}",
                    from.toString(), to.toString()), e);
        }
    }

    @Override
    public List<Event> findEventsByLocation(String location) {
        try{
            return eventDao.findEventsByLocation(location);
        }
        catch (Exception e){
            throw new DAOException(String.format("Error when getting  Events by Location: {0}", location), e);
        }
    }
}
