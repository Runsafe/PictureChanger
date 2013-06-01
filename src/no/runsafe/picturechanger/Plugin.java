package no.runsafe.picturechanger;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.event.hanging.IPaintingPlaced;
import no.runsafe.framework.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.server.entity.RunsafeEntity;
import no.runsafe.framework.server.entity.RunsafePainting;
import no.runsafe.framework.server.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.HashMap;
import java.util.Map;

public class Plugin extends RunsafePlugin implements IPlayerInteractEntityEvent, IPaintingPlaced
{
	@Override
	protected void PluginSetup()
	{
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		RunsafeEntity entity = event.getRightClicked();
		int id = entity.getEntityId();
		if (entity instanceof RunsafePainting && editablePictures.containsKey(id)
			&& event.getPlayer().getName().equals(editablePictures.get(entity.getEntityId())))
		{
			((RunsafePainting) entity).NextArt();
			editablePictures.put(entity.getEntityId(), editablePictures.get(id));
			editablePictures.remove(id);
		}
	}

	@Override
	public boolean OnPaintingPlaced(RunsafePlayer player, RunsafePainting painting)
	{
		editablePictures.put(painting.getEntityId(), player.getName());
		return true;
	}

	private Map<Integer, String> editablePictures = new HashMap<Integer, String>();

}
