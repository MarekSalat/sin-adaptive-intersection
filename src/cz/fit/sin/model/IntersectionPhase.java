package cz.fit.sin.model;

import cz.fit.sin.model.intersection.Intersection;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 9:50
*/
public interface IntersectionPhase {
    /**
     * Set lights according to phase
     * @param intersection
     */
    public void setSemaphoreLights(Intersection intersection);

    /**
     * QueueNum refers to the number of vehicles remain in a lane during a red light phase.
     *
     * QueueNum is the remaining vehicles in a lane during the green signal.
     *
     * @param intersection
     * @return
     */
    public int getQueueNum(Intersection intersection);

    /**
     * FrontNum quantifies the number of vehicles in the link between the affected intersection and the downstream
     * intersections. Information regarding the quantity of vehicles in the front link, left link and
     * the right link are all sent to the fuzzy controller. This input is important in order to avoid congested links
     *
     * FrontNum is referred to as the number of the vehicles in the link where vehicles will go to this link during
     * the green phase
     *
     * @param intersection
     * @return
     */
    public int getFrontNum(Intersection intersection);

    /**
     * RedTime calculates the number of vehicles waiting at a red light. This input is considered as to avoid the
     * drivers waiting too long for the green signal.
     * @param intersection
     * @return
     */
    public int getRedTime(Intersection intersection);
}
