package no.runsafe.picturechanger;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.event.hanging.IPaintingPlaced;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.features.Events;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafePainting;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class Plugin extends RunsafePlugin implements IPlayerInteractEntityEvent, IPaintingPlaced
{
	@Override
	protected void pluginSetup()
	{
		addComponent(Events.class);
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
	public boolean OnPaintingPlaced(IPlayer player, RunsafePainting painting)
	{
		editablePictures.put(painting.getEntityId(), player.getName());
		return true;
	}

	private Map<Integer, String> editablePictures = new HashMap<Integer, String>();

}
