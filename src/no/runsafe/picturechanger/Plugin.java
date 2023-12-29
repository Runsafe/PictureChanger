package no.runsafe.picturechanger;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.hanging.IPaintingPlaced;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.features.Events;
import no.runsafe.framework.minecraft.entity.RunsafePainting;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class Plugin extends RunsafePlugin implements IPlayerInteractEntityEvent, IEntityDamageByEntityEvent, IPaintingPlaced
{
	@Override
	protected void pluginSetup()
	{
		addComponent(Events.class);
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		IEntity entity = event.getRightClicked();
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
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		IEntity entity = event.getEntity();
		if (!(entity instanceof RunsafePainting))
			return;

		IEntity attacker = event.getDamageActor();
		if (!(attacker instanceof IPlayer))
			return;

		if (!((IPlayer) attacker).getName().equals(editablePictures.get(entity.getEntityId())))
			return;

		event.cancel();
		editablePictures.remove(entity.getEntityId());
	}

	@Override
	public boolean OnPaintingPlaced(IPlayer player, RunsafePainting painting)
	{
		editablePictures.put(painting.getEntityId(), player.getName());
		return true;
	}

	private final Map<Integer, String> editablePictures = new HashMap<Integer, String>();
}
